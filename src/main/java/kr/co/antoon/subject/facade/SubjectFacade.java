package kr.co.antoon.subject.facade;

import kr.co.antoon.subject.application.SubjectService;
import kr.co.antoon.subject.application.SubjectRecommendationService;
import kr.co.antoon.subject.domain.vo.SubjectType;
import kr.co.antoon.subject.dto.response.CharacterResponse;
import kr.co.antoon.subject.dto.response.CoupleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SubjectFacade {
    private final SubjectService subjectService;
    private final SubjectRecommendationService subjectRecommendationService;

    @Transactional(readOnly = true)
    public CharacterResponse getCharactersByTopUpper(Long userId) {
        var responses = subjectService.getSubjectsByTopUpper(SubjectType.CHARACTER)
                .stream()
                .map(character ->
                    new CharacterResponse.CharacterDetailResponse(
                        character,
                        subjectRecommendationService.isUserJoin(character.getId(), userId)
                )).toList();
        return new CharacterResponse(responses);
    }

    @Transactional(readOnly = true)
    public CoupleResponse getCouplesByTopUpper(Long userId) {
        var responses = subjectService.getSubjectsByTopUpper(SubjectType.COUPLE)
                .stream()
                .map(couple ->
                    new CoupleResponse.CoupleDetailResponse(
                        couple,
                        subjectRecommendationService.isUserJoin(couple.getId(), userId)
                )).toList();
        return new CoupleResponse(responses);
    }
}
