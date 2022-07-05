package kr.co.antoon.batch.crawling;

import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile({"staging", "prod"})
@Component
@RequiredArgsConstructor
public class CrawlingBatchScheduler {
    private final CrawlingDailyJob crawlingDailyJob;

    /**
     * 매일 오전 3시 10분 스케쥴링
     **/
    //@Scheduled(cron = "0 10 3 * * *")
    public void runDailyJobNaver() {
        crawlingDailyJob.run(Platform.NAVER);
    }

    /**
     * 매일 오전 3시 20분 스케쥴링
     **/
    //@Scheduled(cron = "0 20 3 * * *")
    public void runDailyJobKakao() {
        crawlingDailyJob.run(Platform.KAKAO);
    }
}
