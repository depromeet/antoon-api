package kr.co.toonzip.webtoon.presentation;

import kr.co.toonzip.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebtoonController {
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;

    @PostMapping("/test-crawling")
    public void test() {
        webtoonCrawlingFacade.crawling();
    }
}
