package kr.co.antoon.batch.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraphScoreBatchScheduler {
    private final GraphScoreDailyJob graphScoreDailyJob;

    /**
     * 1시간 마다 그래프 score를 업데이트
     **/
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runDailyJob() {
        graphScoreDailyJob.run();
    }
}