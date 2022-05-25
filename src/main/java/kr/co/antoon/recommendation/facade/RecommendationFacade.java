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
        recommendationCount.plusCount(RecommendationStatus.JOIN);

        recommendationService.save(webtoonId, userId, RecommendationStatus.JOINED);
    }

    @Transactional
    public void saveOrUpdateLeave(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_LEAVED_ERROR);
        }
        RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId)
                .orElseGet(() -> recommendationCountService.save(webtoonId));
        recommendationCount.plusCount(RecommendationStatus.LEAVE);

        recommendationService.save(webtoonId, userId, RecommendationStatus.LEAVED);
    }

    // TODO : 해당 로직이 조금 이상합니다! Null에 대한 방어가 없습니다
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

    @Transactional
    public void updateRecommendationStatus(Long userId, Long webtoonId, RecommendationStatus status) {
        // Logic: 탑승 요청이 들어오면
        // 탑승 -> 탑승 중으로 상태를 업데이트한다.
        // 탑승 인원 수 카운트를 1만큼 증가시킨다.
        // 이미 탑승 중인 경우는 탑승 상태 업데이트가 안된다.
        // 이미 탑승 중인 경우는 탑승 인원 수가 카운트 되지 않는다.

        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.JOINED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        }

        if (recommendationService.existsByUserIdAndWebtoonIdAndStatus(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_LEAVED_ERROR);
        }

        // 1. 탑승 요청인 경우
        if (status.equals(RecommendationStatus.JOIN)) {
            // 탑승 상태 업데이트
            recommendationService.save(userId, webtoonId, RecommendationStatus.JOINED);
            RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId)
                    .orElseGet(() -> recommendationCountService.save(webtoonId));
            // 탑승 인원 수 1 증가
            recommendationCount.plusCount(status);
        }
        if (status.equals(RecommendationStatus.LEAVE)) {
            // 하차 상태 업데이트
            recommendationService.save(userId, webtoonId, RecommendationStatus.LEAVED);
            RecommendationCount recommendationCount = recommendationCountService.findByWebtoonId(webtoonId)
                    .orElseGet(() -> recommendationCountService.save(webtoonId));
            recommendationCount.plusCount(status);
        }
    }
}