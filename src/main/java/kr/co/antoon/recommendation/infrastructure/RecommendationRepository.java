package kr.co.antoon.recommendation.infrastructure;

import kr.co.antoon.recommendation.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Optional<Recommendation> findByMemberIdAndWebtoonId();
}
