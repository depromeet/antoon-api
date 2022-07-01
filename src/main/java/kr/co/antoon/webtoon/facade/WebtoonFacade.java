package kr.co.antoon.webtoon.facade;

import kr.co.antoon.common.util.TimeUtil;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.dto.request.WebtoonSearchRequest;
import kr.co.antoon.webtoon.dto.response.WebtoonAgeResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonDayResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreAllResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonRankingAllResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kr.co.antoon.webtoon.dto.response.WebtoonGenreAllResponse.WebtoonGenrePreviewResponse;

@Component
@RequiredArgsConstructor
public class WebtoonFacade {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final WebtoonGenreService webtoonGenreService;

    @Transactional(readOnly = true)
    public Page<WebtoonDayResponse> getWebtoonByDay(Pageable pageable, String day) {
        return webtoonService.findAllByDay(day, pageable)
                .map(webtoon -> {
                            var writers = webtoonWriterService.findNameByWebtoonId(webtoon.getWebtoonId());

                            return new WebtoonDayResponse(webtoon, writers);
                        }
                );
    }

    @Transactional(readOnly = true)
    public Page<WebtoonGenreResponse> getWebtoonsGenreAndStatus(Pageable pageable, String genre) {
        var end = TimeUtil.now();
        var start = end.minusHours(1);

        var genreCategory = GenreCategory.of(genre);

        return webtoonService.findAllByGenre(start, end, genreCategory, pageable)
                .map(webtoon -> {
                            var writers = webtoonWriterService.findNameByWebtoonId(webtoon.getWebtoonId());

                            return new WebtoonGenreResponse(webtoon, writers, genreCategory);
                        }
                );
    }

    @Cacheable(
            cacheManager = "webtoonCacheManager",
            value = {"webtoon::top::upper"}
    )
    @Transactional(readOnly = true)
    public WebtoonRankingAllResponse getWebtoonsByTopUpper() {
        var webtoons = webtoonService.findAll()
                .parallelStream()
                .collect(Collectors.toMap(Webtoon::getId, webtoon -> webtoon));

        var response = graphScoreSnapshotService.findTop10ByOrderByScoreGap()
                .stream()
                .map(graphScoreSnapshot -> {
                    var webtoon = webtoons.get(graphScoreSnapshot.getWebtoonId());

                    return new WebtoonRankingAllResponse.WebtoonRankingResponse(webtoon, graphScoreSnapshot);
                }).toList();

        return new WebtoonRankingAllResponse(response);
    }

    @Cacheable(
            cacheManager = "webtoonCacheManager",
            value = {"webtoon::age"}
    )
    @Transactional(readOnly = true)
    public WebtoonAgeResponse getAges() {
        var webtoons = webtoonService.findAll();

        var response = IntStream.range(1, 11)
                .mapToObj(i -> webtoonService.findDetailWebtoon(webtoons.get(i).getId()))
                .toList();

        return new WebtoonAgeResponse(response);
    }

    @Cacheable(
            cacheManager = "webtoonCacheManager",
            value = {"webtoon::search"}
    )
    @Transactional(readOnly = true)
    public WebtoonSearchResponse search(WebtoonSearchRequest request) {
        var webtoons = request.webtoons()
                .stream()
                .map(webtoonService::findDetailWebtoon)
                .toList();

        return new WebtoonSearchResponse(webtoons);
    }

    @Cacheable(
            cacheManager = "webtoonCacheManager",
            value = {"webtoon::genre::top3"}
    )
    @Transactional(readOnly = true)
    public WebtoonGenreAllResponse getGenreAndThumbnail() {
        var webtoons = webtoonService.findAll()
                .parallelStream()
                .collect(Collectors.toMap(Webtoon::getId, webtoon -> webtoon));

        var responses = webtoonGenreService.getGenre()
                .stream()
                .map(g -> new WebtoonGenrePreviewResponse(g, webtoons.get(g.getWebtoonId())))
                .toList();

        return new WebtoonGenreAllResponse(responses);
    }
}
