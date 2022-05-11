package kr.co.antoon.graph.infrastructure;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GraphScoreSnapshotRepository extends JpaRepository<GraphScoreSnapshot, Long> {
    List<GraphScoreSnapshot> findAllBySnapshotTime(LocalDateTime time);
    List<GraphScoreSnapshot> findAllByOrderByScoreGapDesc();
    List<GraphScoreSnapshot> findTop9BySnapshotTimeAfterOrderByGraphScoreDescScoreGapDesc(LocalDateTime time);

    Optional<GraphScoreSnapshot> findTop1ByWebtoonIdOrderBySnapshotTimeDesc(Long webtoondId);
}