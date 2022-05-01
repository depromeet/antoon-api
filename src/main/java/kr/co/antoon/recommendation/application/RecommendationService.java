package kr.co.antoon.recommendation.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendationService {
    private final WebtoonRepository webtoonRepository;
    private final RecommendationRepository recommendationRepository;

    @Transactional
    public void updateJoinStatus(Long userId, Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR));
        Recommendation recommendation = recommendationRepository.findByUserIdAndWebtoonId(userId, webtoonId)
                .orElse(null);

        // 탑승 중인 경우
        if (recommendation != null) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_JOINED_ERROR);
        } else {
            recommendationRepository.save(new Recommendation(webtoonId, userId, RecommendationStatus.JOINED));
            webtoonRepository.save(webtoon.plusJoinCount());
        }
    }

    @Transactional
    public void updateLeaveStatus(Long userId, Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR));
        Recommendation recommendation = recommendationRepository.findByUserIdAndWebtoonId(userId, webtoonId)
                .orElse(null);

        // 하차 중인 경우
        if (recommendation != null) {
            throw new AlreadyExistsException((ErrorMessage.ALREADY_LEAVED_ERROR));
        } else {
            recommendationRepository.save(new Recommendation(webtoonId, userId, RecommendationStatus.LEAVED));
            webtoonRepository.save(webtoon.plusLeaveCount());
        }
    }

    @Transactional
    public void deleteAll() {
        var joinedRecommendations = recommendationRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals(RecommendationStatus.JOINED))
                .collect(Collectors.toList());

        for (Recommendation recommendation : joinedRecommendations) {
            Long webtoonId = recommendation.getWebtoonId();
            Webtoon webtoon = webtoonRepository.findById(webtoonId).orElse(null);
            webtoonRepository.save(webtoon.minusJoinCount());
            recommendationRepository.delete(recommendation);
        }

        var leavedRecommendations = recommendationRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals(RecommendationStatus.LEAVED))
                .collect(Collectors.toList());

        for (Recommendation recommendation : leavedRecommendations) {
            Long webtoonId = recommendation.getWebtoonId();
            Webtoon webtoon = webtoonRepository.findById(webtoonId).orElse(null);
            webtoonRepository.save(webtoon.minusLeaveCount());
            recommendationRepository.delete(recommendation);
        }
    }
}
