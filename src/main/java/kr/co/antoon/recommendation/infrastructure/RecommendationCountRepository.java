package kr.co.antoon.recommendation.infrastructure;

import kr.co.antoon.recommendation.domain.RecommendationCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationCountRepository extends JpaRepository<RecommendationCount, Long> {
    Optional<RecommendationCount> findByUserIdAndWebtoonId(Long userId, Long webtoonId);

    Optional<RecommendationCount> findTop1ByWebtoonIdOrderByIdDesc(Long webtoonId);
}