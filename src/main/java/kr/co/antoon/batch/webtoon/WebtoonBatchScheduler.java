package kr.co.antoon.batch.webtoon;

import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class WebtoonBatchScheduler {
    private final WebtoonDailyJob webtoonDailyJob;

    @Scheduled(cron = "0 10 3 * * *")
    public void runDailyJobNaver() {
        webtoonDailyJob.run(Platform.NAVER);
    }

    @Scheduled(cron = "0 20 3 * * *")
    public void runDailyJobKakao() {
        webtoonDailyJob.run(Platform.KAKAO);
    }
}
