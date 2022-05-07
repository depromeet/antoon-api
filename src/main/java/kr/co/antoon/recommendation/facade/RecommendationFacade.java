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
    private final WebtoonService webtoonService;
    private final RecommendationService recommendationService;
    private final RecommendationCountService recommendationCountService;

    @Transactional
    public void updateJoinStatus(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonId(userId, webtoonId, RecommendationStatus.JOINED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        }
        recommendationService.save(webtoonId, userId, RecommendationStatus.JOINED);

        RecommendationCount recommendationCount = recommendationCountService.findByUserIdAndWebtoonId(userId, webtoonId).orElse(null);
        if (recommendationCount != null) {
            int joinCount = recommendationCount.getJoinUserCount();
            recommendationCount.plusJoinCount(joinCount++);
        } else {
            recommendationCountService.save(userId, webtoonId, 1);
        }
    }

    @Transactional
    public void updateLeaveStatus(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonId(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException((ErrorMessage.ALREADY_LEAVED_ERROR));
        }

        recommendationService.save(webtoonId, userId, RecommendationStatus.LEAVED);

        RecommendationCount recommendationCount = recommendationCountService.findByUserIdAndWebtoonId(userId, webtoonId).orElse(null);
        if (recommendationCount != null) {
            int leaveCount = recommendationCount.getLeaveUserCount();
            recommendationCount.plusLeaveCount(leaveCount++);
        } else {
            recommendationCountService.save(userId, webtoonId, 1);
        }
    }

    @Transactional
    public void changeAllStatus() {
        recommendationService.findAllByStatus(RecommendationStatus.JOINED).forEach(recommendation -> {
            recommendation.updateRecommendationStatus(RecommendationStatus.JOIN);
            RecommendationCount recommendationCount = recommendationCountService.findByUserIdAndWebtoonId(
                    recommendation.getUserId(),
                    recommendation.getWebtoonId()).orElse(null);
            recommendationCount.minusJoinCount(recommendationCount.getJoinUserCount() - 1);
        });

        recommendationService.findAllByStatus(RecommendationStatus.LEAVED).forEach(recommendation -> {
            recommendation.updateRecommendationStatus(RecommendationStatus.LEAVE);
            RecommendationCount recommendationCount = recommendationCountService.findByUserIdAndWebtoonId(
                    recommendation.getUserId(),
                    recommendation.getWebtoonId()).orElse(null);
            recommendationCount.minusLeaveCount(recommendationCount.getLeaveUserCount() - 1);
        });
    }
}