package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterHistory;
import kr.co.antoon.character.infrastructure.CharacterHistoryRepository;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterHistoryService {
    private final CharacterHistoryRepository characterHistoryRepository;

    @Transactional
    public CharacterHistory save(Long characterId, Long userId) {
        var characterHistory = new CharacterHistory(characterId, userId);
        return characterHistoryRepository.save(characterHistory);
    }

    @Transactional(readOnly = true)
    public Boolean isUserJoin(Long characterId, AuthInfo info) {
        if (info == null) {
            return false;
        }
        var characterHistory = characterHistoryRepository.findByCharacterIdAndUserId(characterId, info.userId());
        return characterHistory.isPresent();
    }

    @Transactional(readOnly = true)
    public Integer countJoined(Long characterId) {
        return Math.toIntExact(characterHistoryRepository.countByCharacterId(characterId));
    }

    @Transactional(readOnly = true)
    public Boolean existsByUserIdAndCharacterId(Long userId, Long characterId) {
        return characterHistoryRepository.existsByUserIdAndCharacterId(userId, characterId);
    }
}
