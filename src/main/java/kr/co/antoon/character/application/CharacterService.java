package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.VoteType;
import kr.co.antoon.character.infrastructure.CharacterRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final Long AMOUNT = 3L;

    @Transactional(readOnly = true)
    public List<Character> getCharactersByTopUpper(VoteType type) {
        return characterRepository.findTop30ByTypeOrderByAmountDesc(type);
    }

    @Transactional
    public void amountUpdate(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_CHARACTER));
        character.amountUpdate(AMOUNT);
    }
}
