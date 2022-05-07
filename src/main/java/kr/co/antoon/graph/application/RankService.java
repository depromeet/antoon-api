package kr.co.antoon.graph.application;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.TopRank;
import kr.co.antoon.graph.domain.vo.RankReason;
import kr.co.antoon.graph.infrastructure.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RankService {
    private final RankRepository rankRepository;

    @Transactional
    public void saveAll(List<GraphScoreSnapshot> graphScoreSnapshots) {
        rankRepository.saveAll(IntStream.range(0, graphScoreSnapshots.size())
                .mapToObj(g -> new TopRank(
                        g + 1,
                        graphScoreSnapshots.get(g).getId(),
                        RankReason.VERSION_1
                ))
                .collect(Collectors.toList()));
    }
}