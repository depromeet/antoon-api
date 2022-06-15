package kr.co.antoon.subject.application;

import kr.co.antoon.subject.domain.SubjectRecommendation;
import kr.co.antoon.subject.infrastructure.SubjectRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectRecommendationService {
    private final SubjectRecommendationRepository subjectRecommendationRepository;

    @Transactional
    public SubjectRecommendation save(Long characterId, Long userId) {
        SubjectRecommendation subjectRecommendation = new SubjectRecommendation(characterId, userId);
        return subjectRecommendationRepository.save(subjectRecommendation);
    }

    @Transactional(readOnly = true)
    public Boolean isUserJoin(Long characterId, Long userId) {
        var subjectRecommendation = subjectRecommendationRepository.findByCharacterIdAndUserId(characterId, userId);
        return subjectRecommendation.isPresent();
    }
}
