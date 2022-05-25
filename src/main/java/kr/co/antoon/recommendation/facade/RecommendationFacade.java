package kr.co.antoon.recommendation.facade;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.recommendation.application.RecommendationCountService;
import kr.co.antoon.recommendation.application.RecommendationService;
import kr.co.antoon.recommendation.domain.RecommendationCount;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RecommendationFacade {
    private final RecommendationService recommendationService;
    private final RecommendationCountService recommendationCountService;

    @Transactional
    public void saveOrUpdateJoin(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.JOINED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        }
        RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId)
                .orElseGet(() -> recommendationCountService.save(webtoonId));
        recommendationCount.plusJoinCount(recommendationCount.getJoinCount() + 1);
        recommendationService.save(webtoonId, userId, RecommendationStatus.JOINED);
    }

    @Transactional
    public void saveOrUpdateLeave(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_LEAVED_ERROR);
        }
        RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId)
                .orElseGet(() -> recommendationCountService.save(webtoonId));
        recommendationCount.plusLeaveCount(recommendationCount.getLeaveCount() + 1);
        recommendationService.save(webtoonId, userId, RecommendationStatus.LEAVED);
    }

    @Transactional
    public void changeAllStatus() {
        recommendationService.findAllByStatus(RecommendationStatus.JOINED)
                .forEach(recommendation -> {
                    recommendation.updateStatus(RecommendationStatus.JOIN);
                    recommendationCountService.findByWebtoonId(recommendation.getWebtoonId())
                            .ifPresent(recommendationCount -> recommendationCount.minusJoinCount(recommendationCount.getJoinCount() - 1));
                });

        recommendationService.findAllByStatus(RecommendationStatus.LEAVED)
                .forEach(recommendation -> {
                    recommendation.updateStatus(RecommendationStatus.LEAVE);
                    recommendationCountService.findByWebtoonId(recommendation.getWebtoonId())
                            .ifPresent(recommendationCount -> recommendationCount.minusLeaveCount(recommendationCount.getLeaveCount() - 1));
                });
    }
}