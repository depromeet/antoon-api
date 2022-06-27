package kr.co.antoon.webtoon.facade;

import kr.co.antoon.cache.webtoon.WebtoonRedisCacheService;
import kr.co.antoon.cache.webtoon.WebtoonRedisKey;
import kr.co.antoon.crawling.WebtoonCrawlingFactory;
import kr.co.antoon.crawling.dto.WebtoonCrawlingDto;
import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kr.co.antoon.webtoon.converter.WebtoonConverter.toWebtoon;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingFacade {
    private final WebtoonService webtoonService;
    private final WebtoonPublishDayService webtoonPublishDayService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonRedisCacheService webtoonRedisCacheService;

    @Transactional
    public void crawlingWebtoon(Platform platform) {
        var existsWebtoons = webtoonService.findAll()
                .parallelStream()
                .collect(Collectors.toMap(Webtoon::getTitle, ws -> ws, (p1, p2) -> p1));

        List<WebtoonPublishDay> webtoonPublishDays = new ArrayList<>();
        List<WebtoonWriter> webtoonWriters = new ArrayList<>();
        List<WebtoonGenre> webtoonGenres = new ArrayList<>();

        var webtoonCrawling = WebtoonCrawlingFactory.of(platform);

        var aaa = webtoonCrawling.crawling().webtoons()
                .parallelStream()
                .collect(Collectors.toMap(WebtoonCrawlingDto.WebtoonCrawlingDetail::title, dd -> dd, (v1, v2) -> v1));

        Set<WebtoonCrawlingDto.WebtoonCrawlingDetail> bbb = new HashSet<>(aaa.values());

        var webtoonSnapshots = new ArrayList<>(
                bbb
                        .parallelStream()
                        .map(crawlingWebtton -> {
                            Long webtoonId;

                            if (existsWebtoons.containsKey(crawlingWebtton.title())) {
                                Webtoon webtoon = existsWebtoons.get(crawlingWebtton.title());
                                webtoon.update(
                                        crawlingWebtton.content(),
                                        crawlingWebtton.thumbnail(),
                                        crawlingWebtton.url()
                                );
                                webtoonId = webtoon.getId();
                            } else {
                                webtoonId = webtoonService.save(toWebtoon(crawlingWebtton, platform));

                                webtoonWriters.addAll(crawlingWebtton.writer()
                                        .parallelStream()
                                        .map(writer -> new WebtoonWriter(writer, webtoonId))
                                        .toList());

                                webtoonGenres.addAll(crawlingWebtton.genre()
                                        .parallelStream()
                                        .map(genre -> new WebtoonGenre(GenreCategory.of(genre), webtoonId))
                                        .toList());


                                webtoonPublishDays.add(new WebtoonPublishDay(crawlingWebtton.day(), webtoonId));
                            }

                            return new WebtoonSnapshot(crawlingWebtton.score(), webtoonId);
                        }).toList());

        webtoonGenreService.saveAll(webtoonGenres);
        webtoonWriterService.saveAll(webtoonWriters);
        webtoonPublishDayService.saveAll(webtoonPublishDays);
        webtoonSnapshotService.saveAll(webtoonSnapshots);

        webtoonRedisCacheService.evict(WebtoonRedisKey.WEBTOON_SEARCH_KEY);
    }
}
