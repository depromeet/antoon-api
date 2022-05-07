package kr.co.antoon.graph.infrastructure;

import kr.co.antoon.graph.domain.TopRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<TopRank, Long> {
}