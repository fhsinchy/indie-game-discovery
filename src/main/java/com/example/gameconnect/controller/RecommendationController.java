package com.example.gameconnect.controller;

import com.example.gameconnect.dto.GameRecommendation;
import com.example.gameconnect.service.RecommendationService;
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
