package kr.co.antoon.subject.facade;

import kr.co.antoon.subject.application.SubjectService;
import kr.co.antoon.subject.application.SubjectRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SubjectRecommendationFacade {
    private final SubjectRecommendationService subjectRecommendationService;
    private final SubjectService characterService;
    // TODO: enum 관리 필요
    private final static Long JOIN_COIN_AMOUNT = 3L;

    @Transactional
    public void create(Long characterId, Long userId) {
        var subjectRecommendation = subjectRecommendationService.save(characterId, userId);
        //TODO: 사용자 코인 감소 로직 필요
        var character = characterService.findById(characterId);
        character.amountUpdate(JOIN_COIN_AMOUNT);
    }
}
