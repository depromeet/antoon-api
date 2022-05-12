package kr.co.antoon.graph.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.infrastructure.GraphScoreSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GraphScoreSnapshotService {
    private final GraphScoreSnapshotRepository graphScoreSnapshotRepository;

    @Transactional
    public void saveAll(List<GraphScoreSnapshot> graphScoreSnapshots) {
        graphScoreSnapshotRepository.saveAll(graphScoreSnapshots);
    }

    @Transactional(readOnly = true)
    public List<GraphScoreSnapshot> findTop9BySnapshotTimeAfter(LocalDateTime localDateTime) {
        return graphScoreSnapshotRepository.findTop9BySnapshotTimeAfterOrderByGraphScoreDescScoreGapDesc(localDateTime);
    }

    @Transactional(readOnly = true)
    public List<GraphScoreSnapshot> findAllByOrderByScoreGap() {
        return graphScoreSnapshotRepository.findAllByOrderByScoreGapDesc();
    }

    @Transactional(readOnly = true)
    public Optional<GraphScoreSnapshot> findTop1ByWebtoonIdOrderBySnapshotTimeDesc(Long webtoonId) {
        return graphScoreSnapshotRepository.findTop1ByWebtoonIdOrderBySnapshotTimeDesc(webtoonId);
    }

    @Transactional(readOnly = true)
    public long count() {
        return graphScoreSnapshotRepository.count();
    }

    @Transactional(readOnly = true)
    public GraphScoreSnapshot findById(Long id){
        return graphScoreSnapshotRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_GRAPH_SCORE_ERROR));
    }
}