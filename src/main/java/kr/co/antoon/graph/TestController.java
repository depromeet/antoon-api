package kr.co.antoon.graph;

import kr.co.antoon.graph.facade.GraphScoreFacade;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final GraphScoreFacade graphScoreFacade;
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;

    @PostMapping("/test/test/test")
    public void test() {
        graphScoreFacade.snapshot();
    }

    @PostMapping("/test/test")
    public void testtest() {
        webtoonCrawlingFacade.crawlingWebtoon(Platform.NAVER);
    }
}
