package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.dto.reqeust.CharacterRequest;
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

    @Transactional(readOnly = true)
    public List<Character> getSubjectsByTopUpper(CharacterType type) {
        return characterRepository.findTop30ByTypeOrderByCoinAmountDesc(type);
    }

    @Transactional(readOnly = true)
    public Character findById(Long characterId) {
        return characterRepository.findById(characterId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_CHARACTER));
    }

    @Transactional(readOnly = true)
    public Integer findRank(Long characterId) {
        return characterRepository.findRankById(characterId);
    }

    @Transactional
    public Character save(CharacterRequest request, CharacterType type) {
        return characterRepository.save(Character.builder()
                .name(request.name())
                .content(request.content())
                .color(request.color())
                .type(type)
                .webtoonId(request.webtoonId())
                .build()
        );
    }
}
