package kr.co.antoon.graph.facade;

import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.dto.DiscussionCountDto;
import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.application.TopRankService;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.ScoreAllocationCriteria;
import kr.co.antoon.graph.domain.vo.GraphStatus;
import kr.co.antoon.recommendation.application.RecommendationCountService;
import kr.co.antoon.recommendation.domain.RecommendationCount;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
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
    private final DiscussionService discussionService;
    private final WebtoonService webtoonService;
    private final TopRankService topRankService;
    private final RecommendationCountService recommendationCountService;

    /**
     * 인기순(기준)[외부데이터] - 60분마다
     * 1. 플랫폼 전체 평균 별점(50%)외부데이터 크롤링 - 하루 1번 [내부데이터]
     * 2. 우리 사이트내 댓글수(20%)
     * 3. 우리 사이트내 탑승(+)/하차 수(-)(30%)내부데이터 업데이트
     **/
    @Transactional
    public void snapshot() {
        var now = LocalDateTime.now();
        var webtoons = webtoonService.findAll();
        var recommendationCounts = recommendationCountService.findAll()
                .parallelStream()
                .collect(Collectors.toMap(
                                RecommendationCount::getWebtoonId,
                                rc -> rc,
                                (rc1, rc2) -> rc1
                        )
                );

        var discussionCounts = discussionService.discussionCount()
                .parallelStream()
                .collect(Collectors.toMap(
                        DiscussionCountDto::getWebtoonId,
                        dc -> dc,
                        (dc1, dc2) -> dc1
                ));

        graphScoreSnapshotService.saveAll(webtoons
                .parallelStream()
                .map(w -> {
                    var webtoonId = w.getId();

                    var webtoonScore = ScoreAllocationCriteria.DEFAULT_WEBTOON_SCORE;
                    var webtoonSnapshot = webtoonSnapshotService.findTop1ByWebtoonIdOrderBySnapshotTimeDesc(webtoonId);
                    if (webtoonSnapshot.isPresent()) {
                        webtoonScore = ScoreAllocationCriteria.webtoonScore(webtoonSnapshot.get().getScore());
                    }

                    var discussionScore = 0L;
                    if (!Objects.isNull(discussionCounts.get(webtoonId))) {
                        discussionScore = discussionCounts.get(webtoonId).getDiscussionCount();
                    }

                    var recommendationScore = ScoreAllocationCriteria.DEFAULT_RECOMMENDATION_SCORE;
                    if (recommendationCounts.containsKey(webtoonId)) {
                        int count = recommendationCounts.get(webtoonId).count();
                        recommendationScore = ScoreAllocationCriteria.recommendationScore(count);
                    }

                    var graphScore = ScoreAllocationCriteria.graphScore((int) discussionScore, recommendationScore, webtoonScore);

                    var graphScoreSnapshots = graphScoreSnapshotService.findTop1ByWebtoonIdOrderBySnapshotTimeDesc(webtoonId);

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
        topRankService.saveAll(graphScoreSnapshots);
    }
}