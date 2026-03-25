package dev.farhan.discovery.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;
    private String username;
    private Preferences preferences;
    private List<GameRating> ratings;

    public UserProfile() {
    }

    public UserProfile(String username, Preferences preferences) {
        this.username = username;
        this.preferences = preferences;
        this.ratings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public List<GameRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<GameRating> ratings) {
        this.ratings = ratings;
    }
}
