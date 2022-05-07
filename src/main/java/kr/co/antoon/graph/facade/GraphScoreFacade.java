package kr.co.antoon.graph.facade;

import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.application.TopRankService;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.vo.GraphStatus;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GraphScoreFacade {
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonService webtoonService;
    private final TopRankService rankService;

    @Transactional
    public void snapshot() {
        var now = LocalDateTime.now();
        var webtoons = webtoonService.findAllByStatus(ActiveStatus.PUBLISH);

        var webtoonSnapshotsAboutToday = webtoonSnapshotService.findAllBySnapshopTime(now.toLocalDate());

        if (webtoonSnapshotsAboutToday.size() == 0) {
            webtoonSnapshotsAboutToday = webtoonSnapshotService.findAllBySnapshopTime(now.toLocalDate().minusDays(1));
        }

        var webtoonSnapshotsAboutYesterday = graphScoreSnapshotService.findAllBySnapshotTime(now.minusDays(1));

        var webtoonSnapshots = webtoonSnapshotsAboutToday.stream()
                .collect(Collectors.toMap(WebtoonSnapshot::getWebtoonId, ws -> ws));

        graphScoreSnapshotService.saveAll(webtoons.stream()
                .filter(w -> webtoonSnapshots.containsKey(w.getId()))
                .map(w -> {
                    var score = webtoonSnapshots.get(w.getId()).getScore();

                    var gap = webtoonSnapshotsAboutYesterday.stream()
                            .filter(wsay -> Objects.equals(w.getId(), wsay.getWebtoonId()))
                            .findFirst()
                            .map(wsay -> score - wsay.getGraphScore())
                            .orElse(score);

                    return GraphScoreSnapshot.of(
                            score,
                            gap,
                            w.getId(),
                            GraphStatus.of(gap)
                    );
                }).toList());

        rankService.saveAll(graphScoreSnapshotService.findTop9BySnapshotTimeAfter(now));
    }
}