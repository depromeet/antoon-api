package kr.co.antoon.batch.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraphScoreBatchScheduler {
    private final GraphScoreHourJob graphScoreHourJob;

    /**
     * 1시간 마다 그래프 score를 업데이트
     **/
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runHourJob() {
        graphScoreHourJob.run();
    }
}