package kr.co.antoon.webtoon.presentation;

import kr.co.antoon.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class WebtoonController {
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;

    /**
     * For Crawling Test
     **/
    @PostMapping("/test-crawling")
    public void test() {
       webtoonCrawlingFacade.crawlingWebtoon();
    }
}
