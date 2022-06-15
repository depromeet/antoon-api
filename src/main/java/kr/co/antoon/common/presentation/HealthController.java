package kr.co.antoon.common.presentation;

import kr.co.antoon.graph.facade.GraphScoreFacade;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.facade.WebtoonCrawlingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {
    private final WebtoonCrawlingFacade webtoonCrawlingFacade;
    private final GraphScoreFacade graphScoreFacade;

    @GetMapping("/health")
    public String health() {
        webtoonCrawlingFacade.crawlingWebtoon(Platform.NAVER);
        webtoonCrawlingFacade.crawlingWebtoon(Platform.KAKAO);
        graphScoreFacade.snapshot();
        return "Health Good!";
    }
}