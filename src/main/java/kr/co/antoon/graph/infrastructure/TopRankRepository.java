package kr.co.antoon.graph.infrastructure;

import kr.co.antoon.graph.domain.TopRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopRankRepository extends JpaRepository<TopRank, Long> {
    List<TopRank> findDistinctTop9ByOrderByRankTimeDescRankingAsc();
}
