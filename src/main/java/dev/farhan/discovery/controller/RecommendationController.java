package dev.farhan.discovery.controller;

import dev.farhan.discovery.dto.GameRecommendation;
import dev.farhan.discovery.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GameRecommendation>> getRecommendations(@PathVariable String userId) {
        List<GameRecommendation> recommendations =
                recommendationService.getCombinedRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}
