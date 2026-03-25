package dev.farhan.discovery.service;

import dev.farhan.discovery.domain.Game;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] generateGameEmbedding(Game game) {
        String text = game.getDescription() + " "
                + "Genres: " + String.join(", ", game.getGenres()) + ". "
                + "Tags: " + String.join(", ", game.getTags()) + ". "
                + "Mechanics: " + String.join(", ", game.getMechanics()) + ".";
        return embeddingModel.embed(text);
    }
}
