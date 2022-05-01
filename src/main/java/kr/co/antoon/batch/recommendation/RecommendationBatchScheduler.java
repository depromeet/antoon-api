package kr.co.antoon.batch.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class RecommendationBatchScheduler {
    private final RecommendationDailyJob recommendationDailyJob;

    // 매일 낮 12시에 탑승중 상태 초기화
    @Scheduled(cron = "0 0 12 * * ?")
    public void runDailyJobJoinedStatus() {
        recommendationDailyJob.run();
    }

    // 매일 낮 12시에 하차중 상태 초기화
    @Scheduled(cron = "0 0 12 * * ?")
    public void runDailyJobLeavedStatus() {
        recommendationDailyJob.run();
    }
}
