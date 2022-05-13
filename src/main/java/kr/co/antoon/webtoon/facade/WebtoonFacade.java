package kr.co.antoon.webtoon.facade;

import kr.co.antoon.common.Utility;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.dto.response.WebtoonDayResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonRankingAllResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WebtoonFacade {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonPublishDayService webtoonPublishDayService;
    private final GraphScoreSnapshotService graphScoreSnapshotService;

    @Transactional(readOnly = true)
    public WebtoonResponse getWebtoon(Long id) {
        var webtoon = webtoonService.findById(id);
        var writer = webtoonWriterService.findNameByWebtoonId(id);
        var category = webtoonGenreService.findCategoryByWebtoonId(id);
        var days = webtoonPublishDayService.findDaysByWebtoonId(id);

        return new WebtoonResponse(webtoon, writer, category, days);
    }

    @Transactional(readOnly = true)
    public Page<WebtoonDayResponse> getWebtoonByDay(Pageable pageable, String day) {
        var webtoons = webtoonService.findAllByStatus(ActiveStatus.PUBLISH)
                .parallelStream()
                .filter(webtoon -> webtoonPublishDayService.existsByWebtoonIdAndDay(webtoon.getId(), day))
                .collect(Collectors.toMap(Webtoon::getId, webtoon -> webtoon));

        var webtoonDayResponses = graphScoreSnapshotService.findAllByOrderByScoreGap()
                .parallelStream()
                .filter(graphScoreSnapshot -> webtoons.containsKey(graphScoreSnapshot.getWebtoonId()))
                .map(graphScoreSnapshot -> {
                    var webtoon = webtoons.get(graphScoreSnapshot.getWebtoonId());
                    return new WebtoonDayResponse(
                            webtoon.getThumbnail(),
                            webtoon.getTitle(),
                            webtoonWriterService.findNameByWebtoonId(graphScoreSnapshot.getWebtoonId()),
                            day
                    );
                }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), webtoonDayResponses.size());
        return new PageImpl<>(webtoonDayResponses.subList(start, end), pageable, webtoonDayResponses.size());
    }

    @Transactional(readOnly = true)
    public PageImpl<WebtoonGenreResponse> getWebtoonsGenreAndStatus(Pageable pageable, String genre) {
        var webtoons = webtoonService.findWebtoonByGenreAndStatus(pageable, genre, ActiveStatus.PUBLISH)
                .parallelStream()
                .collect(Collectors.toMap(Webtoon::getId, webtoon -> webtoon));

        var webtoonGenreResponses = graphScoreSnapshotService.findAllByOrderByScoreGap()
                .parallelStream()
                .filter(graphScoreSnapshot -> webtoons.containsKey(graphScoreSnapshot.getWebtoonId()))
                .map(graphScoreSnapshot -> {
                    var webtoon = webtoons.get(graphScoreSnapshot.getWebtoonId());
                    return new WebtoonGenreResponse(
                            webtoon.getThumbnail(),
                            webtoon.getTitle(),
                            graphScoreSnapshot.getGraphScore(),
                            Utility.getDifferencePercentage(graphScoreSnapshot.getGraphScore(), graphScoreSnapshot.getScoreGap()),
                            webtoonWriterService.findNameByWebtoonId(graphScoreSnapshot.getWebtoonId()),
                            webtoon.getPlatform(),
                            genre
                    );
                }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), webtoonGenreResponses.size());
        return new PageImpl<>(webtoonGenreResponses.subList(start, end), pageable, webtoonGenreResponses.size());
    }

    @Transactional(readOnly = true)
    public WebtoonRankingAllResponse getWebtoonsByTopUp() {
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