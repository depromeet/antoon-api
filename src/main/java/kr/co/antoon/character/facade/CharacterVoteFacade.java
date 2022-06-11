package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.application.CharacterVoteService;
import kr.co.antoon.character.domain.CharacterVote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CharacterVoteFacade {
    private final CharacterVoteService characterVoteService;
    private final CharacterService characterService;

    @Transactional
    public void create(Long characterId, Long userId) {
        characterVoteService.save(characterId, userId);
        //TODO: 사용자 코인 감소 로직 필요
        characterService.amountUpdate(characterId);
    }
}
