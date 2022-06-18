package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.application.CharacterHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CharacterHistoryFacade {
    private final CharacterHistoryService characterHistoryService;
    private final CharacterService characterService;
    // TODO: enum 관리 필요
    private final static Long JOIN_COIN_AMOUNT = 3L;

    @Transactional
    public void create(Long characterId, Long userId) {
        characterHistoryService.save(characterId, userId);
        //TODO: 사용자 코인 감소 로직 필요
        var joinedItem = characterService.findById(characterId);
        joinedItem.amountUpdate(JOIN_COIN_AMOUNT);
    }
}
