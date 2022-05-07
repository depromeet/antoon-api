package kr.co.antoon.recommendation.application;

import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    @Transactional(readOnly = true)
    public boolean existsByUserIdAndWebtoonId(Long userId, Long webtoonId, RecommendationStatus status) {
        return recommendationRepository.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, status);
    }

    @Transactional
    public void save(Long userId, Long webtoonId, RecommendationStatus status) {
        recommendationRepository.save(
                new Recommendation(
                        userId,
                        webtoonId,
                        status
                )
        );
    }

    @Transactional(readOnly = true)
    public List<Recommendation> findAllByStatus(RecommendationStatus status) {
        return recommendationRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}