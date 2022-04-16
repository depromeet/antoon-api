package kr.co.antoon.graph.infrastructure;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphScoreSnapshotRepository extends JpaRepository<GraphScoreSnapshot, Long> {
}