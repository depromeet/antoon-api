package kr.co.antoon.batch.webtoon;

import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingDailyJob {
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;

    public void run(Platform platform) {
        webtoonCrawlingFacade.crawlingWebtoon(platform);
    }
}