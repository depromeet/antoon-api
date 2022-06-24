package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterHistoryService;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.dto.response.CoupleResponse;
import kr.co.antoon.character.dto.response.PersonaResponse;
import kr.co.antoon.webtoon.application.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CharacterFacade {
    private final WebtoonService webtoonService;
    private final CharacterService characterService;
    private final CharacterHistoryService characterHistoryService;

    @Transactional(readOnly = true)
    public PersonaResponse getPersonasByTopUpper(Long userId) {
        var responses = characterService.getSubjectsByTopUpper(CharacterType.PERSONA)
                .stream()
                .map(character -> {
                    var webtoon = webtoonService.findById(character.getWebtoonId());
                    return new PersonaResponse.CharacterDetailResponse(
                            character,
                            webtoon.getTitle(),
                            characterHistoryService.isUserJoin(character.getId(), userId)
                    );
                }).toList();
        return new PersonaResponse(responses);
    }

    @Transactional(readOnly = true)
    public CoupleResponse getCouplesByTopUpper(Long userId) {
        var responses = characterService.getSubjectsByTopUpper(CharacterType.COUPLE)
                .stream()
                .map(couple -> {
                    var webtoon = webtoonService.findById(couple.getWebtoonId());
                    return new CoupleResponse.CoupleDetailResponse(
                            couple,
                            webtoon.getTitle(),
                            characterHistoryService.isUserJoin(couple.getId(), userId)
                    );
                }).toList();
        return new CoupleResponse(responses);
    }
}
