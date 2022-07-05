package kr.co.antoon.batch.cruiser;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile({"staging", "prod"})
@Component
@RequiredArgsConstructor
public class CruiserBatchScheduler {
    private final CruiserHourJob cruiserHourJob;

    /**
     * 1시간 마다 데이터 통계를 Sender한다.
     **/
    //@Scheduled(cron = "0 0 0/1 * * *")
    public void runHourJob() {
        cruiserHourJob.run();
    }
}
