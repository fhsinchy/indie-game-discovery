package dev.farhan.discovery.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import dev.farhan.discovery.domain.Preferences;
import dev.farhan.discovery.domain.UserProfile;
import dev.farhan.discovery.dto.GameRecommendation;
import dev.farhan.discovery.repository.UserProfileRepository;

@Service
public class RecommendationService {

    private final MongoTemplate mongoTemplate;
    private final UserProfileRepository userProfileRepository;
    private final EmbeddingModel embeddingModel;

    public RecommendationService(MongoTemplate mongoTemplate,
                                 UserProfileRepository userProfileRepository,
                                 EmbeddingModel embeddingModel) {
        this.mongoTemplate = mongoTemplate;
        this.userProfileRepository = userProfileRepository;
        this.embeddingModel = embeddingModel;
    }

    public List<GameRecommendation> getRecommendations(String userId) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Preferences prefs = user.getPreferences();

        Document genreScoreExpr = buildScoreExpression(prefs.getGenres(), "$genres");
        Document tagScoreExpr = buildScoreExpression(prefs.getTags(), "$tags");
        Document mechanicScoreExpr = buildScoreExpression(prefs.getMechanics(), "$mechanics");

        Document totalScore = new Document("$add", List.of(genreScoreExpr, tagScoreExpr, mechanicScoreExpr));

        AggregationOperation addScoreField = context ->
                new Document("$addFields", new Document("score", totalScore));

        AggregationOperation sortByScore = context ->
                new Document("$sort", new Document("score", -1));

        Aggregation aggregation = Aggregation.newAggregation(addScoreField, sortByScore);

        AggregationResults<GameRecommendation> results =
                mongoTemplate.aggregate(aggregation, "games", GameRecommendation.class);

        return results.getMappedResults();
    }

    private Document buildScoreExpression(Map<String, Double> preferenceMap, String gameField) {
        if (preferenceMap == null || preferenceMap.isEmpty()) {
            return new Document("$literal", 0.0);
        }

        Document prefObject = new Document();
        preferenceMap.forEach(prefObject::append);

        Document prefArray = new Document("$objectToArray", new Document("$literal", prefObject));

        Document prefKeys = new Document("$map",
                new Document("input", prefArray)
                        .append("as", "pref")
                        .append("in", "$$pref.k"));

        Document matchedKeys = new Document("$setIntersection", List.of(prefKeys, gameField));

        Document matchedEntries = new Document("$filter",
                new Document("input", prefArray)
                        .append("as", "entry")
                        .append("cond", new Document("$in", List.of("$$entry.k", matchedKeys))));

        return new Document("$reduce",
                new Document("input", matchedEntries)
                        .append("initialValue", 0.0)
                        .append("in", new Document("$add", List.of("$$value", "$$this.v"))));
    }

    public List<GameRecommendation> findSimilarGames(String userId) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Preferences prefs = user.getPreferences();
        String preferenceText = buildPreferenceText(prefs);
        float[] queryVector = embeddingModel.embed(preferenceText);

        List<Double> queryVectorList = new ArrayList<>();
        for (float f : queryVector) {
            queryVectorList.add((double) f);
        }

        Document vectorSearchStage = new Document("$vectorSearch",
                new Document("index", "vector_index")
                        .append("path", "embedding")
                        .append("queryVector", queryVectorList)
                        .append("numCandidates", 50)
                        .append("limit", 10));

        AggregationOperation vectorSearch = context -> vectorSearchStage;

        AggregationOperation addScore = context ->
                new Document("$addFields",
                        new Document("score", new Document("$meta", "vectorSearchScore")));

        Aggregation aggregation = Aggregation.newAggregation(vectorSearch, addScore);

        AggregationResults<GameRecommendation> results =
                mongoTemplate.aggregate(aggregation, "games", GameRecommendation.class);

        return results.getMappedResults();
    }

    private String buildPreferenceText(Preferences prefs) {
        List<String> parts = new ArrayList<>();
        prefs.getGenres().entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> parts.add(e.getKey()));
        prefs.getTags().entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> parts.add(e.getKey()));
        prefs.getMechanics().entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> parts.add(e.getKey()));
        return "Games with " + String.join(", ", parts);
    }

    public List<GameRecommendation> getCombinedRecommendations(String userId) {
        List<GameRecommendation> contentResults = getRecommendations(userId);
        List<GameRecommendation> similarityResults = findSimilarGames(userId);

        double maxContentScore = contentResults.stream()
                .mapToDouble(GameRecommendation::getScore)
                .max().orElse(1.0);

        double maxSimilarityScore = similarityResults.stream()
                .mapToDouble(GameRecommendation::getScore)
                .max().orElse(1.0);

        Map<String, GameRecommendation> merged = new LinkedHashMap<>();

        for (GameRecommendation rec : contentResults) {
            rec.setContentScore(rec.getScore() / maxContentScore);
            rec.setSimilarityScore(0.0);
            merged.put(rec.getId(), rec);
        }

        for (GameRecommendation rec : similarityResults) {
            double normalizedSimilarity = rec.getScore() / maxSimilarityScore;
            if (merged.containsKey(rec.getId())) {
                GameRecommendation existing = merged.get(rec.getId());
                existing.setSimilarityScore(normalizedSimilarity);
            } else {
                rec.setContentScore(0.0);
                rec.setSimilarityScore(normalizedSimilarity);
                merged.put(rec.getId(), rec);
            }
        }

        for (GameRecommendation rec : merged.values()) {
            double combined = 0.6 * rec.getContentScore() + 0.4 * rec.getSimilarityScore();
            rec.setCombinedScore(Math.round(combined * 1000.0) / 1000.0);
        }

        return merged.values().stream()
                .sorted(Comparator.comparingDouble(GameRecommendation::getCombinedScore).reversed())
                .toList();
    }
}
