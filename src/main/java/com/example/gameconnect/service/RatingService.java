package com.example.gameconnect.service;

import com.example.gameconnect.domain.Game;
import com.example.gameconnect.domain.GameRating;
import com.example.gameconnect.domain.Preferences;
import com.example.gameconnect.domain.UserProfile;
import com.example.gameconnect.dto.RatingRequest;
import com.example.gameconnect.repository.GameRepository;
import com.example.gameconnect.repository.UserProfileRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private static final double LEARNING_RATE = 0.15;

    private final MongoTemplate mongoTemplate;
    private final GameRepository gameRepository;
    private final UserProfileRepository userProfileRepository;

    public RatingService(MongoTemplate mongoTemplate,
                         GameRepository gameRepository,
                         UserProfileRepository userProfileRepository) {
        this.mongoTemplate = mongoTemplate;
        this.gameRepository = gameRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile applyRating(String userId, RatingRequest request) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new RuntimeException("Game not found: " + request.gameId()));

        int score = request.score();
        if (score == 3) {
            pushRatingOnly(userId, request);
            return userProfileRepository.findById(userId).orElseThrow();
        }

        double targetDirection = score >= 4 ? 1.0 : 0.0;
        Preferences prefs = user.getPreferences();

        Map<String, Double> updatedGenres = adjustWeights(prefs.getGenres(), game.getGenres(), targetDirection);
        Map<String, Double> updatedTags = adjustWeights(prefs.getTags(), game.getTags(), targetDirection);
        Map<String, Double> updatedMechanics = adjustWeights(prefs.getMechanics(), game.getMechanics(), targetDirection);

        updateProfileInMongo(userId, request, updatedGenres, updatedTags, updatedMechanics);

        return userProfileRepository.findById(userId).orElseThrow();
    }

    private Map<String, Double> adjustWeights(Map<String, Double> currentWeights,
                                              List<String> gameAttributes,
                                              double targetDirection) {
        Map<String, Double> updated = new HashMap<>(currentWeights);
        for (String attribute : gameAttributes) {
            double oldWeight = updated.getOrDefault(attribute, 0.5);
            double newWeight = oldWeight + LEARNING_RATE * (targetDirection - oldWeight);
            updated.put(attribute, Math.round(newWeight * 1000.0) / 1000.0);
        }
        return updated;
    }

    private void pushRatingOnly(String userId, RatingRequest request) {
        GameRating rating = new GameRating(request.gameId(), request.score(), Instant.now());
        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update().push("ratings", rating);
        mongoTemplate.updateFirst(query, update, UserProfile.class);
    }

    private void updateProfileInMongo(String userId, RatingRequest request,
                                      Map<String, Double> genres,
                                      Map<String, Double> tags,
                                      Map<String, Double> mechanics) {
        GameRating rating = new GameRating(request.gameId(), request.score(), Instant.now());
        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update()
                .set("preferences.genres", genres)
                .set("preferences.tags", tags)
                .set("preferences.mechanics", mechanics)
                .push("ratings", rating);
        mongoTemplate.updateFirst(query, update, UserProfile.class);
    }
}
