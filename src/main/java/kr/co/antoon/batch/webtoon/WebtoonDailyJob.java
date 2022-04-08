package kr.co.antoon.batch.webtoon;

import kr.co.antoon.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebtoonDailyJob {
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;

    public void run() {
        webtoonCrawlingFacade.crawlingWebtoon();
    }
}