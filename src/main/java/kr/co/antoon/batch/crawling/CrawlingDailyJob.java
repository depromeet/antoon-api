package kr.co.antoon.batch.crawling;

import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlingDailyJob {
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;

    public void run(Platform platform) {
        webtoonCrawlingFacade.crawlingWebtoon(platform);
    }
}
