package kr.co.antoon.graph.facade;

import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.vo.GraphStatus;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GraphScoreFacade {
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonService webtoonService;

    // TODO : 댓글, 좋아요에 대한 통계 자료를 추가적으로 삽입해야 함
    @Transactional
    public void snapshot() {
        var webtoons = webtoonService.findAllByStatus(ActiveStatus.PUBLISH);

        var webtoonSnapshots = webtoonSnapshotService.findAllBySnapshopTime(LocalDate.now());

        var yesterday = LocalDateTime.now().minusDays(1);
        var webtoonSnapshotsAboutYesterday = graphScoreSnapshotService.findAllBySnapshotTime(yesterday);


        List<GraphScoreSnapshot> graphScoreSnapshots = new ArrayList<>();
        for (Webtoon webtoon : webtoons) {
            for (WebtoonSnapshot webtoonSnapshot : webtoonSnapshots) {
                if (Objects.equals(webtoon.getId(), webtoonSnapshot.getWebtoonId())) {

                    double score = webtoonSnapshot.getScore();

                    GraphStatus realStatus = GraphStatus.MAINTATIN;

                    for (GraphScoreSnapshot graphScoreSnapshot : webtoonSnapshotsAboutYesterday) {
                        if (Objects.equals(webtoon.getId(), graphScoreSnapshot.getWebtoonId())) {
                            realStatus = GraphStatus.of(graphScoreSnapshot.getGraphScore(), score);
                        }
                    }

                    graphScoreSnapshots.add(
                            GraphScoreSnapshot.of(
                                    webtoonSnapshot.getScore(),
                                    webtoon.getId(),
                                    realStatus
                            ));
                }
            }
        }


        graphScoreSnapshotService.saveAll(graphScoreSnapshots);
    }
}