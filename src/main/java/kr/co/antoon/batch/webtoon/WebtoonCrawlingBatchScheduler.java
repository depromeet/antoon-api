package kr.co.antoon.batch.webtoon;

import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingBatchScheduler {
    private final WebtoonCrawlingDailyJob webtoonCrawlingDailyJob;

    /**
     * 매일 오전 3시 10분 스케쥴링
     **/
    @Scheduled(cron = "0 10 3 * * *")
    public void runDailyJobNaver() {
        webtoonCrawlingDailyJob.run(Platform.NAVER);
    }

    /**
     * 매일 오전 3시 20분 스케쥴링
     **/
    @Scheduled(cron = "0 20 3 * * *")
    public void runDailyJobKakao() {
        webtoonCrawlingDailyJob.run(Platform.KAKAO);
    }
}
