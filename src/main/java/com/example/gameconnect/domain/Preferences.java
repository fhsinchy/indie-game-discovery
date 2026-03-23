package com.example.gameconnect.domain;

import java.util.Map;

public class Preferences {

    private Map<String, Double> genres;
    private Map<String, Double> tags;
    private Map<String, Double> mechanics;

    public Preferences() {
    }

    public Preferences(Map<String, Double> genres, Map<String, Double> tags,
                       Map<String, Double> mechanics) {
        this.genres = genres;
        this.tags = tags;
        this.mechanics = mechanics;
    }

    public Map<String, Double> getGenres() {
        return genres;
    }

    public void setGenres(Map<String, Double> genres) {
        this.genres = genres;
    }

    public Map<String, Double> getTags() {
        return tags;
    }

    public void setTags(Map<String, Double> tags) {
        this.tags = tags;
    }

    public Map<String, Double> getMechanics() {
        return mechanics;
    }

    public void setMechanics(Map<String, Double> mechanics) {
        this.mechanics = mechanics;
    }
}
