package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.application.CharacterVoteService;
import kr.co.antoon.character.domain.vo.VoteType;
import kr.co.antoon.character.dto.response.CharacterResponse;
import kr.co.antoon.character.dto.response.CoupleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CharacterFacade {
    private final CharacterService characterService;
    private final CharacterVoteService voteCharacterService;

    @Transactional(readOnly = true)
    public CharacterResponse getCharactersByTopUpper(Long userId) {
        var responses = characterService.getCharactersByTopUpper(VoteType.CHARACTER)
                .stream()
                .map(character ->
                    new CharacterResponse.CharacterDetailResponse(
                        character,
                        voteCharacterService.isUserJoin(character.getId(), userId)
                )).toList();
        return new CharacterResponse(responses);
    }

    @Transactional(readOnly = true)
    public CoupleResponse getCouplesByTopUpper(Long userId) {
        var responses = characterService.getCharactersByTopUpper(VoteType.COUPLE)
                .stream()
                .map(character ->
                        new CoupleResponse.CoupleDetailResponse(
                                character,
                                voteCharacterService.isUserJoin(character.getId(), userId)
                        )).toList();
        return new CoupleResponse(responses);
    }

}
