package kr.co.antoon.graph.application;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.TopRank;
import kr.co.antoon.graph.domain.vo.RankReason;
import kr.co.antoon.graph.infrastructure.TopRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TopRankService {
    private final TopRankRepository topRankRepository;

    @Transactional
    public void saveAll(List<GraphScoreSnapshot> graphScoreSnapshots) {
        topRankRepository.saveAll(IntStream.range(0, graphScoreSnapshots.size())
                        .mapToObj(g -> TopRank.builder()
                                .ranking(g + 1)
                                .graphScoreSnapshotId(graphScoreSnapshots.get(g).getId())
                                .reason(RankReason.VERSION_1)
                                .webtoonId(graphScoreSnapshots.get(g).getWebtoonId())
                                .build()
                        ).collect(Collectors.toList()));
    }

    @Transactional
    public List<TopRank> findTopRank() {
        return topRankRepository.findDistinctTop9ByOrderByRankTimeDesc();
    }
}