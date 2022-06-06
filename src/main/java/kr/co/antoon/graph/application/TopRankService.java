package kr.co.antoon.graph.application;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.TopRank;
import kr.co.antoon.graph.domain.vo.RankReason;
import kr.co.antoon.graph.infrastructure.TopRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TopRankService {
    private final TopRankRepository topRankRepository;

    @Transactional
    public void saveAll(List<GraphScoreSnapshot> graphScoreSnapshots) {
        var response = IntStream.range(0, graphScoreSnapshots.size())
                .mapToObj(g -> TopRank.builder()
                        .ranking(g + 1)
                        .graphScoreSnapshotId(graphScoreSnapshots.get(g).getId())
                        .reason(RankReason.TOTAL_SCORE)
                        .webtoonId(graphScoreSnapshots.get(g).getWebtoonId())
                        .build()
                ).toList();

        topRankRepository.saveAll(response);
    }

    @Transactional
    public List<TopRank> findTopRank() {
        return topRankRepository.findDistinctTop9ByOrderByRankTimeDescRankingAsc();
    }
}