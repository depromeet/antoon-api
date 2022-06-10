package kr.co.antoon.recommendation.facade;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.recommendation.application.RecommendationCountService;
import kr.co.antoon.recommendation.application.RecommendationService;
import kr.co.antoon.recommendation.domain.RecommendationCount;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.dto.response.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RecommendationFacade {
    private final RecommendationService recommendationService;
    private final RecommendationCountService recommendationCountService;

    @Transactional
    public RecommendationResponse saveOrUpdate(RecommendationStatus status, Long userId, Long webtoonId) {
        statusCheck(userId, webtoonId);
        RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId)
                .orElseGet(() -> recommendationCountService.save(webtoonId));
        recommendationCount.updateCount(status);

        status = RecommendationStatus.of(status);
        recommendationService.save(webtoonId, userId, status);
        return new RecommendationResponse(
                recommendationCount,
                status
        );
    }

    @Transactional(readOnly = true)
    public void statusCheck(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_LEAVED_ERROR);
        }
        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.JOINED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        }
    }

    @Transactional
    public void changeAllStatus() {
        recommendationService.findAllByStatus(RecommendationStatus.JOINED)
                .forEach(recommendation -> {
                    recommendation.updateStatus(RecommendationStatus.NONE);
                    recommendationCountService.findByWebtoonId(recommendation.getWebtoonId())
                            .ifPresent(rc -> rc.updateCount(RecommendationStatus.JOINED));
                });

        recommendationService.findAllByStatus(RecommendationStatus.LEAVED)
                .forEach(recommendation -> {
                    recommendation.updateStatus(RecommendationStatus.NONE);
                    recommendationCountService.findByWebtoonId(recommendation.getWebtoonId())
                            .ifPresent(rc -> rc.updateCount(RecommendationStatus.LEAVED));
                });
    }
}