package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.crawling.WebtoonCrawlingFactory;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonGenre;
import kr.co.antoon.webtoon.domain.WebtoonWriter;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
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

        WebtoonCrawlingFactory.of(platform)
                .crawling()
                .webtoons()
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

                    webtoonWriterService.saveAll(crawlingWebtton.writer()
                            .stream()
                            .map(writer -> new WebtoonWriter(writer, webtoonId))
                            .collect(Collectors.toList()));

                    webtoonGenreService.saveAll(crawlingWebtton.genre()
                            .stream()
                            .map(genre -> new WebtoonGenre(Category.of(genre), webtoonId))
                            .collect(Collectors.toList()));

                    webtoonPublishDayService.save(crawlingWebtton.day(), webtoonId);
                    webtoonSnapshotService.save(crawlingWebtton.score(), webtoonId);
                });
    }

    private boolean isNotUpdated(
            Map<String, Webtoon> webtoons,
            WebtoonCrawlingDto.WebtoonCrawlingDetail crawlingWebtton
    ) {
        if (webtoons.containsKey(crawlingWebtton.title())) {
            webtoons.get(crawlingWebtton.title())
                    .update(
                            crawlingWebtton.title(),
                            crawlingWebtton.content(),
                            crawlingWebtton.thumbnail(),
                            crawlingWebtton.url()
                    );
            return false;
        }
        return true;
    }
}