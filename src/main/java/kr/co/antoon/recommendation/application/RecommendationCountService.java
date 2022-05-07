package kr.co.antoon.recommendation.application;

import kr.co.antoon.recommendation.domain.RecommendationCount;
import kr.co.antoon.recommendation.infrastructure.RecommendationCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationCountService {
    private final RecommendationCountRepository recommendationCountRepository;

    @Transactional(readOnly = true)
    public Optional<RecommendationCount> findByUserIdAndWebtoonId(Long userId, Long webtoonId) {
        return recommendationCountRepository.findByUserIdAndWebtoonId(userId, webtoonId);
    }

    @Transactional
    public RecommendationCount save(Long userId, Long webtoonId, int joinCount) {
        return recommendationCountRepository.save(
                new RecommendationCount(
                        userId,
                        webtoonId,
                        joinCount
                )
        );
    }
}
