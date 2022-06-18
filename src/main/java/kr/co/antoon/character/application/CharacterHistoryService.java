package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterHistory;
import kr.co.antoon.character.infrastructure.CharacterHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterHistoryService {
    private final CharacterHistoryRepository characterHistoryRepository;

    @Transactional
    public CharacterHistory save(Long characterId, Long userId) {
        CharacterHistory subjectRecommendation = new CharacterHistory(characterId, userId);
        return characterHistoryRepository.save(subjectRecommendation);
    }

    @Transactional(readOnly = true)
    public Boolean isUserJoin(Long characterId, Long userId) {
        var subjectRecommendation = characterHistoryRepository.findByCharacterIdAndUserId(characterId, userId);
        return subjectRecommendation.isPresent();
    }
}
