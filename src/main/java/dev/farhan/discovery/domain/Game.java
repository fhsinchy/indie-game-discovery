package dev.farhan.discovery.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "games")
public class Game {

    @Id
    private String id;
    private String title;
    private String description;
    private String developer;
    private List<String> genres;
    private List<String> tags;
    private List<String> mechanics;
    private double rating;
    private int releaseYear;
    private float[] embedding;

    public Game() {
    }

    public Game(String title, String description, String developer,
                List<String> genres, List<String> tags, List<String> mechanics,
                double rating, int releaseYear) {
        this.title = title;
        this.description = description;
        this.developer = developer;
        this.genres = genres;
        this.tags = tags;
        this.mechanics = mechanics;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<String> mechanics) {
        this.mechanics = mechanics;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }
}
