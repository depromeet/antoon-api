package kr.co.antoon.graph.facade;

import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonSnapshotService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GraphScoreFacade {
    private final GraphScoreSnapshotService graphScoreSnapshotService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonService webtoonService;

    @Transactional
    public void snapshot() {

        // 활성화된 웹툰 전체
        List<Webtoon> webtoons = webtoonService.findAllByStatus(ActiveStatus.PUBLISH);

        // 웹툰 스냅샷
        List<WebtoonSnapshot> webtoonSnapshots = webtoonSnapshotService.findAllBySnapshopTime(LocalDate.now());

        // TODO GRAPH SCORE SNAPSHOT 로직 구현 필요

        List<GraphScoreSnapshot> graphScoreSnapshots = new ArrayList<>();

        graphScoreSnapshotService.saveAll(graphScoreSnapshots);
    }
}