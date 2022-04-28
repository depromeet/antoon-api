package kr.co.antoon.batch.recommendation;

import kr.co.antoon.recommendation.facade.RecommendationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RecommendationDailyJob {
    private final RecommendationFacade recommendationFacade;

    public void run() {
        recommendationFacade.deleteAllRecommendationStatus();
    }
}
