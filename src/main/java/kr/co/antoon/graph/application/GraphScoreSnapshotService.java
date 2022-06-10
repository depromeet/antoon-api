package kr.co.antoon.graph.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.vo.Period;
import kr.co.antoon.graph.dto.response.GraphScoreResponse;
import kr.co.antoon.graph.infrastructure.GraphScoreSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Optional<GraphScoreSnapshot> findTop1ByWebtoonIdOrderBySnapshotTimeDesc(Long webtoonId) {
        return graphScoreSnapshotRepository.findTop1ByWebtoonIdOrderBySnapshotTimeDesc(webtoonId);
    }

    @Transactional(readOnly = true)
    public long count() {
        return graphScoreSnapshotRepository.count();
    }

    @Transactional(readOnly = true)
    public GraphScoreSnapshot findById(Long id) {
        return graphScoreSnapshotRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_GRAPH_SCORE_ERROR));
    }

    @Transactional(readOnly = true)
    public GraphScoreResponse graphByDays(Long webtoonId, Period period) {
        var end = LocalDateTime.now();
        var start = end.minusDays(period.getDays());

        var scores = graphScoreSnapshotRepository.findAllByWebtoonIdAndSnapshotTimeBetweenOrderByCreatedAtAsc(webtoonId, start, end)
                .stream()
                .map(GraphScoreResponse.GraphScoreDetail::new)
                .toList();

        return new GraphScoreResponse(scores);
    }

    @Transactional(readOnly = true)
    public GraphScoreResponse graphByMoreThanWeek(Long webtoonId, Period period) {
        var end = LocalDateTime.now();
        var start = end.minusDays(period.getDays());

        var graphScoreSnapshots = Stream
                .iterate(start, date -> date.isBefore(end), date -> date.plusDays(1))
                .map(date -> graphScoreSnapshotRepository.findTopOneByWebtoonIdAndSnapshotTimeBetweenOrderByCreatedAtDesc(webtoonId, start, end))
                .toList();

        var score = graphScoreSnapshots.stream()
                .map(GraphScoreResponse.GraphScoreDetail::new)
                .toList();

        return new GraphScoreResponse(score);
    }

    @Transactional(readOnly = true)
    public List<GraphScoreSnapshot> findTop10ByOrderByScoreGap() {
        var end = LocalDateTime.now();
        var start = end.minusHours(1);

        return graphScoreSnapshotRepository.findDistinctTop10BySnapshotTimeBetweenOrderByScoreGapDescGraphScoreDesc(start, end);
    }


}