package kr.co.antoon.graph.facade;

import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.application.TopRankService;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.vo.GraphStatus;
import kr.co.antoon.graph.infrastructure.GraphScoreSnapshotRepository;
import kr.co.antoon.recommendation.application.RecommendationCountService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GraphScoreFacade {
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final DiscussionService discussionService;
    private final WebtoonService webtoonService;
    private final TopRankService rankService;
    private final RecommendationCountService recommendationCountService;
    private final GraphScoreSnapshotRepository graphScoreSnapshotRepository;

    /**
     * 인기순(기준)[외부데이터]
     * 1. 플랫폼 전체 평균 별점(50%)외부데이터 크롤링 - 하루 1번 [내부데이터]
     * 2. 우리 사이트내 댓글수(20%)
     * 3. 우리 사이트내 탑승(+)/하차 수(-)(30%)내부데이터 업데이트 - 60분마다
     **/
    @Transactional
    public void snapshot() {
        var now = LocalDateTime.now();

        var webtoons = webtoonService.findAllByStatus(ActiveStatus.PUBLISH);
        var webtoonSnapshotsAboutToday = webtoonSnapshotService.findAllBySnapshopTime(now.toLocalDate());

        if (webtoonSnapshotsAboutToday.size() == 0) {
            webtoonSnapshotsAboutToday = webtoonSnapshotService.findAllBySnapshopTime(now.toLocalDate().minusDays(1));
        }

        var webtoonSnapshots = webtoonSnapshotsAboutToday.stream()
                .collect(Collectors.toMap(WebtoonSnapshot::getWebtoonId, ws -> ws));

        graphScoreSnapshotService.saveAll(webtoons.stream()
                .filter(w -> webtoonSnapshots.containsKey(w.getId()))
                .map(w -> {
                    var webtoonId = w.getId();

                    var discussionScore = discussionService.countById(webtoonId) * 1000 / 0.2;

                    var recommendationCount = recommendationCountService.findTop1ByWebtoonIdOrderByCreatedAtDesc(webtoonId);
                    var recommendationScore = 0.0;
                    if (recommendationCount.isPresent()) {
                        recommendationScore = recommendationCount.get().count() * 1000 / 0.3;
                    }

                    var webtoonScore = webtoonSnapshots.get(webtoonId).getScore() * 1000 / 0.5;

                    var graphScore = (discussionScore + recommendationScore + webtoonScore) / 1000;

                    Optional<GraphScoreSnapshot> graphScoreSnapshots = graphScoreSnapshotRepository.findTop1ByWebtoonIdOrderBySnapshotTimeDesc(webtoonId);

                    var scoregap = graphScore;
                    if (graphScoreSnapshots.isPresent()) {
                        scoregap = scoregap - graphScoreSnapshots.get().getGraphScore();
                    }

                    return GraphScoreSnapshot.of(
                            graphScore,
                            scoregap,
                            webtoonId,
                            GraphStatus.of(scoregap)
                    );
                }).toList());

        var graphScoreSnapshots = graphScoreSnapshotService.findTop9BySnapshotTimeAfter(now);
        rankService.saveAll(graphScoreSnapshots);
    }
}