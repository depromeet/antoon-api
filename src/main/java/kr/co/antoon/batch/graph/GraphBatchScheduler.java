package kr.co.antoon.batch.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile({"staging", "prod"})
@Component
@RequiredArgsConstructor
public class GraphBatchScheduler {
    private final GraphHourJob graphHourJob;

    /**
     * 1시간 마다 그래프 score를 업데이트
     **/
    //@Scheduled(cron = "0 59 * * * *")
    public void runHourJob() {
        graphHourJob.run();
    }
}
