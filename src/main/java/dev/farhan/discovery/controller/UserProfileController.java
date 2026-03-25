package dev.farhan.discovery.controller;

import dev.farhan.discovery.domain.UserProfile;
import dev.farhan.discovery.dto.CreateUserRequest;
import dev.farhan.discovery.dto.RatingRequest;
import dev.farhan.discovery.repository.UserProfileRepository;
import dev.farhan.discovery.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;
    private final RatingService ratingService;

    public UserProfileController(UserProfileRepository userProfileRepository,
                                 RatingService ratingService) {
        this.userProfileRepository = userProfileRepository;
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody CreateUserRequest request) {
        UserProfile profile = new UserProfile(request.username(), request.preferences());
        UserProfile saved = userProfileRepository.save(profile);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUser(@PathVariable String userId) {
        return userProfileRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/ratings")
    public ResponseEntity<UserProfile> rateGame(@PathVariable String userId,
                                                @RequestBody RatingRequest request) {
        UserProfile updatedProfile = ratingService.applyRating(userId, request);
        return ResponseEntity.ok(updatedProfile);
    }
}
