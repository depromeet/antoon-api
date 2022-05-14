package kr.co.antoon.graph.facade;

import kr.co.antoon.graph.application.GraphScoreSnapshotService;
import kr.co.antoon.graph.application.TopRankService;
import kr.co.antoon.graph.dto.response.TopRankResponse;
import kr.co.antoon.webtoon.application.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TopRankFacade {
    private final TopRankService topRankService;
    private final WebtoonService webtoonService;
    private final GraphScoreSnapshotService graphScoreSnapshotService;

    @Transactional(readOnly = true)
    public TopRankResponse findTopRank() {
        return new TopRankResponse(topRankService.findTopRank()
                .parallelStream()
                .map(tr -> new TopRankResponse.TopRankWebtooon(
                        tr.getRanking(),
                        graphScoreSnapshotService.findById(tr.getGraphScoreSnapshotId()),
                        webtoonService.findDetailWebtoon(tr.getWebtoonId())
                )).collect(Collectors.toList()));
    }
}