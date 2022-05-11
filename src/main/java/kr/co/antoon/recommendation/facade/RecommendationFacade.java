package kr.co.antoon.recommendation.facade;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.recommendation.application.RecommendationCountService;
import kr.co.antoon.recommendation.application.RecommendationService;
import kr.co.antoon.recommendation.domain.RecommendationCount;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.webtoon.application.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class RecommendationFacade {
    private final RecommendationService recommendationService;
    private final RecommendationCountService recommendationCountService;

    @Transactional
    public void updateJoinStatus(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonId(userId, webtoonId, RecommendationStatus.JOINED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        }
        recommendationService.save(webtoonId, userId, RecommendationStatus.JOINED);

        RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId).orElse(null);
        if (recommendationCount != null) {
            int joinCount = recommendationCount.getJoinCount();
            recommendationCount.plusJoinCount(joinCount++);
        } else {
            recommendationCountService.save(webtoonId, 1);
        }
    }

    @Transactional
    public void updateLeaveStatus(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonId(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException((ErrorMessage.ALREADY_LEAVED_ERROR));
        }

        recommendationService.save(webtoonId, userId, RecommendationStatus.LEAVED);

        RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId).orElse(null);
        if (recommendationCount != null) {
            int leaveCount = recommendationCount.getLeaveCount();
            recommendationCount.plusLeaveCount(leaveCount++);
        } else {
            recommendationCountService.save(webtoonId, 1);
        }
    }

    @Transactional
    public void changeAllStatus() {
        recommendationService.findAllByStatus(RecommendationStatus.JOINED).forEach(recommendation -> {
            recommendation.updateRecommendationStatus(RecommendationStatus.JOIN);
            RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(
                    recommendation.getWebtoonId()).orElse(null);
            recommendationCount.minusJoinCount(recommendationCount.getJoinCount() - 1);
        });

        recommendationService.findAllByStatus(RecommendationStatus.LEAVED).forEach(recommendation -> {
            recommendation.updateRecommendationStatus(RecommendationStatus.LEAVE);
            RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(
                    recommendation.getWebtoonId()).orElse(null);
            recommendationCount.minusLeaveCount(recommendationCount.getLeaveCount() - 1);
        });
    }
}