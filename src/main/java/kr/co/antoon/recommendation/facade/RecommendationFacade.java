package kr.co.antoon.recommendation.facade;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.recommendation.application.RecommendationService;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class RecommendationFacade {
    private final WebtoonService webtoonService;
    private final RecommendationService recommendationService;

    @Transactional
    public void updateJoinStatus(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonId(userId, webtoonId, RecommendationStatus.JOINED)) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        }
        recommendationService.save(webtoonId, userId, RecommendationStatus.JOINED);
        webtoonService.findById(webtoonId).plusJoinCount();
    }

    @Transactional
    public void updateLeaveStatus(Long userId, Long webtoonId) {
        if (recommendationService.existsByUserIdAndWebtoonId(userId, webtoonId, RecommendationStatus.LEAVED)) {
            throw new AlreadyExistsException((ErrorMessage.ALREADY_LEAVED_ERROR));
        }

        recommendationService.save(webtoonId, userId, RecommendationStatus.LEAVED);
        webtoonService.findById(webtoonId).plusLeaveCount();
    }

    @Transactional
    public void deleteAll() {
        recommendationService.findAllByStatus(RecommendationStatus.JOINED).forEach(recommendation -> {
            webtoonService.findById(recommendation.getWebtoonId()).minusJoinCount();
            recommendationService.delete(recommendation);
        });

        recommendationService.findAllByStatus(RecommendationStatus.LEAVED).forEach(recommendation -> {
            webtoonService.findById(recommendation.getWebtoonId()).minusLeaveCount();
            recommendationService.delete(recommendation);
        });
    }
}