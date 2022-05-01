package kr.co.antoon.recommendation.infrastructure;

import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Boolean existsByUserIdAndWebtoonIdAndStatus(long userId, long webtoonId, RecommendationStatus status);
}