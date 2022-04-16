package kr.co.antoon.graph.application;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.infrastructure.GraphScoreSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphScoreSnapshotService {
    private final GraphScoreSnapshotRepository graphScoreSnapshotRepository;

    @Transactional
    public void saveAll(List<GraphScoreSnapshot> graphScoreSnapshots) {
        graphScoreSnapshotRepository.saveAll(graphScoreSnapshots);
    }
}