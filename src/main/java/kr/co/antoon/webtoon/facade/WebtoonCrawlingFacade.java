package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.*;
import kr.co.antoon.webtoon.crawling.WebtoonCrawling;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonGenre;
import kr.co.antoon.webtoon.domain.WebtoonWriter;
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
        var existsWebtoons = webtoonService.findAll();
        var crawlingWebtoons = webtoonCrawling.crawling();

        crawlingWebtoons.dto()
                .stream()
                .filter(crawlingWebtton -> isNotUpdated(existsWebtoons, crawlingWebtton))
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

                    List<WebtoonGenre> genres = crawlingWebtton.genre()
                            .stream()
                            .map(w -> new WebtoonGenre(Category.of(w), webtoonId))
                            .collect(Collectors.toList());

                    List<WebtoonWriter> writers = crawlingWebtton.writer()
                            .stream()
                            .map(w -> new WebtoonWriter(w, webtoonId))
                            .collect(Collectors.toList());

                    webtoonWriterService.saveAll(writers);
                    webtoonGenreService.saveAll(genres);
                    webtoonPublishDayService.save(crawlingWebtton.day(), webtoonId);
                    webtoonSnapshotService.save(crawlingWebtton.score(), webtoonId);
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
                        crawlingWebtton.thumbnail(),
                        crawlingWebtton.url()
                );
                return false;
            }
        }
        return true;
    }
}