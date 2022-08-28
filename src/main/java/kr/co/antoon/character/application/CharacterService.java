package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.infrastructure.CharacterRepository;
import kr.co.antoon.error.exception.character.NotExistsCharacterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;

    @Transactional(readOnly = true)
    public List<Character> getCharactersByTopUpper(CharacterType type) {
        return characterRepository.findTop30ByTypeOrderByCoinAmountDesc(type);
    }

    @Transactional(readOnly = true)
    public Character findById(Long characterId) {
        return characterRepository.findById(characterId)
                .orElseThrow(NotExistsCharacterException::new);
    }

    @Transactional(readOnly = true)
    public Integer findRank(Long characterId) {
        return characterRepository.findRankById(characterId);
    }

    @Transactional(readOnly = true)
    public void existsById(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new NotExistsCharacterException();
        }
    }
}
