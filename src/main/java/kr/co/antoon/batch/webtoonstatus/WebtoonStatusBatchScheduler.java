package kr.co.antoon.batch.webtoonstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("batch")
@Component
@RequiredArgsConstructor
public class WebtoonStatusBatchScheduler {
    private final WebtoonStatusDailyJob webtoonStatusDailyJob;

    /**
     * 매일 낮 12시에 탑승중, 하차중 상태 초기화
     **/
    @Scheduled(cron = "0 0 12 * * ?")
    public void runDailyJobLeavedStatus() {
        webtoonStatusDailyJob.run();
    }
}
