package kr.co.antoon.webtoon.facade;

import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.dto.response.WebtoonDayResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreAllResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonRankingAllResponse;
import kr.co.antoon.webtoon.infrastructure.WebtoonGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WebtoonFacade {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final WebtoonGenreRepository webtoonGenreRepository;

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
    public PageImpl<WebtoonGenreResponse> getWebtoonsGenreAndStatus(Pageable pageable, String genre) {
        var webtoons = webtoonService.findWebtoonByGenreAndStatus(genre, ActiveStatus.PUBLISH)
                .parallelStream()
                .collect(Collectors.toMap(Webtoon::getId, webtoon -> webtoon));

        var webtoonGenreResponses = graphScoreSnapshotService.findAllByOrderByScoreGap()
                .parallelStream()
                .filter(graphScoreSnapshot -> webtoons.containsKey(graphScoreSnapshot.getWebtoonId()))
                .map(graphScoreSnapshot -> {
                    var webtoon = webtoons.get(graphScoreSnapshot.getWebtoonId());
                    var writers = webtoonWriterService.findNameByWebtoonId(graphScoreSnapshot.getWebtoonId());

                    return new WebtoonGenreResponse(webtoon, graphScoreSnapshot, writers, genre);
                }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), webtoonGenreResponses.size());
        return new PageImpl<>(webtoonGenreResponses.subList(start, end), pageable, webtoonGenreResponses.size());
    }

    @Transactional(readOnly = true)
    public WebtoonGenreAllResponse getWebtoonsGenres() {
        var end = LocalDateTime.now();
        var start = end.minusHours(1);

        var response = Arrays.stream(GenreCategory.values())
                .map(category -> webtoonService.findGenre(start, end, category))
                .flatMap(Collection::stream)
                .map(WebtoonGenreAllResponse.WebtoonGenrePreviewResponse::new)
                .toList();

        return new WebtoonGenreAllResponse(response);
    }

    @Transactional(readOnly = true)
    public WebtoonRankingAllResponse getWebtoonsByTopUpper() {
        List<WebtoonRankingAllResponse.WebtoonRankingResponse> responses = new ArrayList<>();
        var webtoons = webtoonService.findAllByStatus(ActiveStatus.PUBLISH)
                .stream()
                .collect(Collectors.toMap(Webtoon::getId, webtoon -> webtoon));

        graphScoreSnapshotService.findAllByOrderByScoreGap()
                .stream()
                .limit(10)
                .forEachOrdered(graphScoreSnapshot -> {
                    var webtoon = webtoons.get(graphScoreSnapshot.getWebtoonId());
                    responses.add(new WebtoonRankingAllResponse.WebtoonRankingResponse(
                            webtoon.getId(),
                            webtoon.getWebtoonUrl(),
                            webtoon.getThumbnail(),
                            webtoon.getTitle(),
                            graphScoreSnapshot.getGraphScore(),
                            graphScoreSnapshot.getScoreGapPercent(),
                            graphScoreSnapshot.getSnapshotTime(),
                            webtoon.getStatus().getDescription(),
                            webtoon.getPlatform()
                    ));
                });
        return new WebtoonRankingAllResponse(responses);
    }
}