package kr.co.toonzip.batch.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// @Profile("prod") 운영 배포에서만 스케쥴링 동작
@Component
@RequiredArgsConstructor
public class WebtoonBatchScheduler {
    private final WebtoonDailyJob webtoonDailyJob;

    /**
     * WebToon Crawling 작업을 매일 오전 8시에 스케쥴링한다.
     **/
    @Scheduled(cron = "0 0 8 * * *")
    public void runDailyJob() {
        webtoonDailyJob.run();
    }
}
