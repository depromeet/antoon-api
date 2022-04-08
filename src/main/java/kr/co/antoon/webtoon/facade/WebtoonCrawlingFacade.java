package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.crawling.WebtoonCrawling;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonPublishDay;
import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingFacade {
    private final WebtoonService webtoonService;
    private final WebtoonPublishDayService webtoonPublishDayService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonCrawling webtoonCrawling;

    @Transactional
    public void crawlingWebtoon() {
        var insertedWebtoons = webtoonService.findAll();
        var crawlingWebtoons = webtoonCrawling.crawling();

        crawlingWebtoons.dto()
                .stream()
                .filter(crawlingWebtton -> isNotUpdated(insertedWebtoons, crawlingWebtton))
                .forEach(crawlingWebtton -> {
                    Long webtoonId = webtoonService.save(
                            Webtoon.builder()
                                    .title(crawlingWebtton.title())
                                    .content(crawlingWebtton.content())
                                    .writer(crawlingWebtton.writer())
                                    .url(crawlingWebtton.url())
                                    .thumbnail(crawlingWebtton.thumbnail())
                                    .genre(crawlingWebtton.genre())
                                    .platform(Platform.NAVER)
                                    .build()
                    );
                    webtoonPublishDayService.save(new WebtoonPublishDay(crawlingWebtton.day(), webtoonId));
                    webtoonSnapshotService.save(new WebtoonSnapshot(crawlingWebtton.score(), webtoonId));
                });
    }

    private boolean isNotUpdated(
            List<Webtoon> insertedWebtoons,
            WebtoonCrawlingDto.WebtoonCrawlingDetail crawlingWebtton
    ) {
        for (var webtoon : insertedWebtoons) {
            if (webtoon.isEqualsTitle(crawlingWebtton.title())) {
                webtoon.update(
                        crawlingWebtton.title(),
                        crawlingWebtton.content(),
                        crawlingWebtton.writer(),
                        crawlingWebtton.thumbnail(),
                        crawlingWebtton.url(),
                        crawlingWebtton.genre()
                );
                return false;
            }
        }
        return true;
    }
}