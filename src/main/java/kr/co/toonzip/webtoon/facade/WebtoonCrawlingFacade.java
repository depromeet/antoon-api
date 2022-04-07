package kr.co.toonzip.webtoon.facade;

import kr.co.toonzip.webtoon.application.WebtoonPublishDayService;
import kr.co.toonzip.webtoon.application.WebtoonService;
import kr.co.toonzip.webtoon.application.WebtoonSnapshotService;
import kr.co.toonzip.webtoon.crawling.WebtoonCrawling;
import kr.co.toonzip.webtoon.domain.Webtoon;
import kr.co.toonzip.webtoon.domain.WebtoonPublishDay;
import kr.co.toonzip.webtoon.domain.WebtoonSnapshot;
import kr.co.toonzip.webtoon.domain.vo.Platform;
import kr.co.toonzip.webtoon.dto.WebtoonCrawlingDto;
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

        crawlingWebtoons.getDto()
                .stream()
                .filter(crawlingWebtton -> isNotUpdated(insertedWebtoons, crawlingWebtton))
                .forEach(crawlingWebtton -> {
                    Long webtoonId = webtoonService.save(
                            Webtoon.builder()
                                    .title(crawlingWebtton.getTitle())
                                    .content(crawlingWebtton.getContent())
                                    .writer(crawlingWebtton.getWriter())
                                    .url(crawlingWebtton.getUrl())
                                    .thumbnail(crawlingWebtton.getThumbnail())
                                    .genre(crawlingWebtton.getGenre())
                                    .platform(Platform.NAVER)
                                    .build()
                    );
                    webtoonPublishDayService.save(new WebtoonPublishDay(crawlingWebtton.getDay(), webtoonId));
                    webtoonSnapshotService.save(new WebtoonSnapshot(crawlingWebtton.getScore(), webtoonId));
                });
    }

    private boolean isNotUpdated(
            List<Webtoon> insertedWebtoons,
            WebtoonCrawlingDto.WebtoonCrawlingDetail crawlingWebtton
    ) {
        for (var webtoon : insertedWebtoons) {
            if (webtoon.isEqualsTitle(crawlingWebtton.getTitle())) {
                webtoon.update(
                        crawlingWebtton.getTitle(),
                        crawlingWebtton.getContent(),
                        crawlingWebtton.getWriter(),
                        crawlingWebtton.getThumbnail(),
                        crawlingWebtton.getUrl(),
                        crawlingWebtton.getGenre()
                );
                return false;
            }
        }
        return true;
    }
}