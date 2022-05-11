package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.crawling.WebtoonCrawlingFactory;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonGenre;
import kr.co.antoon.webtoon.domain.WebtoonPublishDay;
import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.domain.WebtoonWriter;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingFacade {
    private final WebtoonService webtoonService;
    private final WebtoonPublishDayService webtoonPublishDayService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonWriterService webtoonWriterService;

    @Transactional
    public void crawlingWebtoon(Platform platform) {
        var existsWebtoons = webtoonService.findAll()
                .stream()
                .collect(Collectors.toMap(Webtoon::getTitle, ws -> ws, (p1, p2) -> p1));

        List<WebtoonPublishDay> webtoonPublishDays = new ArrayList<>();
        List<WebtoonSnapshot> webtoonSnapshots = new ArrayList<>();
        List<WebtoonWriter> webtoonWriters = new ArrayList<>();
        List<WebtoonGenre> webtoonGenres = new ArrayList<>();

        WebtoonCrawlingFactory.of(platform)
                .crawling()
                .webtoons()
                .forEach(crawlingWebtton -> {
                    Long webtoonId;

                    if (existsWebtoons.containsKey(crawlingWebtton.title())) {
                        Webtoon webtoon = existsWebtoons.get(crawlingWebtton.title());
                        webtoon.update(
                                crawlingWebtton.title(),
                                crawlingWebtton.content(),
                                crawlingWebtton.thumbnail(),
                                crawlingWebtton.url()
                        );
                        webtoonId = webtoon.getId();
                    } else {
                        webtoonId = webtoonService.save(
                                Webtoon.builder()
                                        .title(crawlingWebtton.title())
                                        .content(crawlingWebtton.content())
                                        .webtoonUrl(crawlingWebtton.url())
                                        .thumbnail(crawlingWebtton.thumbnail())
                                        .platform(platform)
                                        .build()
                        );

                        crawlingWebtton.writer().forEach(writer ->
                                webtoonWriters.add(new WebtoonWriter(writer, webtoonId))
                        );

                        crawlingWebtton.genre().forEach(genre ->
                                webtoonGenres.add(new WebtoonGenre(GenreCategory.of(genre), webtoonId))
                        );

                        webtoonPublishDays.add(new WebtoonPublishDay(crawlingWebtton.day(), webtoonId));
                    }

                    webtoonSnapshots.add(new WebtoonSnapshot(crawlingWebtton.score(), webtoonId));
                });

        webtoonGenreService.saveAll(webtoonGenres);
        webtoonWriterService.saveAll(webtoonWriters);
        webtoonPublishDayService.saveAll(webtoonPublishDays);
        webtoonSnapshotService.saveAll(webtoonSnapshots);
    }
}