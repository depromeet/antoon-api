package kr.co.antoon.recommendation.facade;

import kr.co.antoon.recommendation.application.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendationFacade {
    private final RecommendationService recommendationService;

    public void deleteAllRecommendationStatus() {
        recommendationService.deleteAll();
    }
}
