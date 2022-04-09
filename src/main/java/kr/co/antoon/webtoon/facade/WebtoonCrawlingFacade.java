package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.*;
import kr.co.antoon.webtoon.crawling.WebtoonCrawling;
import kr.co.antoon.webtoon.domain.*;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingFacade {
    private final WebtoonService webtoonService;
    private final WebtoonPublishDayService webtoonPublishDayService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonCrawling webtoonCrawling;
    private final WebtoonWriterService webtoonWriterService;

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
                                    .url(crawlingWebtton.url())
                                    .thumbnail(crawlingWebtton.thumbnail())
                                    .platform(Platform.NAVER)
                                    .build()
                    );

                    List<WebtoonGenre> webtoonGenres = crawlingWebtton.genre()
                            .stream()
                            .map(wg -> new WebtoonGenre(Category.of(wg), webtoonId))
                            .collect(Collectors.toList());

                    List<WebtoonWriter> webtoonWriters = crawlingWebtton.writer()
                            .stream()
                            .map(ww -> new WebtoonWriter(ww, webtoonId))
                            .collect(Collectors.toList());

                    webtoonWriterService.saveAll(webtoonWriters);
                    webtoonGenreService.saveAll(webtoonGenres);
                    webtoonPublishDayService.save(new WebtoonPublishDay(crawlingWebtton.day(), webtoonId));
                    webtoonSnapshotService.save(new WebtoonSnapshot(crawlingWebtton.score(), webtoonId));
                });
    }

    // todo : 수정 필요
    private boolean isNotUpdated(
            List<Webtoon> insertedWebtoons,
            WebtoonCrawlingDto.WebtoonCrawlingDetail crawlingWebtton
    ) {
        for (var webtoon : insertedWebtoons) {
            if (webtoon.isEqualsTitle(crawlingWebtton.title())) {
                webtoon.update(
                        crawlingWebtton.title(),
                        crawlingWebtton.content(),
                        crawlingWebtton.thumbnail(),
                        crawlingWebtton.url()
                );
                return false;
            }
        }
        return true;
    }
}