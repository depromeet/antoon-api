package kr.co.antoon.recommendation.application;

import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    @Transactional(readOnly = true)
    public boolean existsByUserIdAndWebtoonIdAndStatus(Long userId, Long webtoonId, RecommendationStatus status) {
        return recommendationRepository.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, status);
    }

    @Transactional
    public void save(Long userId, Long webtoonId, RecommendationStatus status) {
        var recommendation = new Recommendation(userId, webtoonId, status);

        recommendationRepository.save(recommendation);
    }

    @Transactional(readOnly = true)
    public List<Recommendation> findAllByStatus(RecommendationStatus status) {
        return recommendationRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals(status))
                .toList();
    }
}
