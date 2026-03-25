package dev.farhan.discovery.domain;

import java.time.Instant;

public class GameRating {

    private String gameId;
    private int score;
    private Instant ratedAt;

    public GameRating() {
    }

    public GameRating(String gameId, int score, Instant ratedAt) {
        this.gameId = gameId;
        this.score = score;
        this.ratedAt = ratedAt;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Instant getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Instant ratedAt) {
        this.ratedAt = ratedAt;
    }
}
