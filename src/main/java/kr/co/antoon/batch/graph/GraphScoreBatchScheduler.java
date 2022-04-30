package kr.co.antoon.batch.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class GraphScoreBatchScheduler {
    private final GraphScoreDailyJob graphScoreDailyJob;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void runDailyJob() {
        graphScoreDailyJob.run();
    }
}