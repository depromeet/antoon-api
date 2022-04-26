package kr.co.antoon.recommendation.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RecommendationService {
    private final WebtoonRepository webtoonRepository;
    private final RecommendationRepository recommendationRepository;

    @Transactional
    public boolean updateJoinStatus(Long webtoonId, Long memberId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR));
        Recommendation recommendation = recommendationRepository.findByMemberIdAndWebtoonId().orElse(null);

        // 이미 탑승해요 버튼을 눌러서 탑승 중인 경우 탑승이 안됨
        if (recommendation != null) {
           return false;
        } else {    // 탑승 중이 아닌 경우
            recommendationRepository.save(new Recommendation(webtoonId, memberId));
            webtoonRepository.save(webtoon.plusJoinMemberCount());
            return true;
        }
    }

    public boolean updateLeaveStatus(Long webtoonId, Long memberId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR));
        Recommendation recommendation = recommendationRepository.findByMemberIdAndWebtoonId().orElse(null);

        if (recommendation != null) {
            return false;
        } else {
            recommendationRepository.save(new Recommendation(webtoonId, memberId));
            webtoonRepository.save(webtoon.plusLeaveMemberCount());
            return true;
        }
    }
}
