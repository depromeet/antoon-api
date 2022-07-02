package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterHistoryService;
import kr.co.antoon.character.application.CharacterImageService;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.domain.vo.CharacterImageType;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.dto.response.CharacterDetailResponse;
import kr.co.antoon.character.dto.response.CharacterResponse;
import kr.co.antoon.oauth.dto.AuthInfo;
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
    private final CharacterImageService characterImageService;

    @Transactional(readOnly = true)
    public CharacterResponse getTopUpper(String type, AuthInfo info) {
        var responses = characterService.getCharactersByTopUpper(CharacterType.valueOf(type))
                .stream()
                .map(character -> {
                    var webtoon = webtoonService.findById(character.getWebtoonId());
                    var characterImage = characterImageService.findByCharacterIdAndType(
                            character.getId(),
                            CharacterImageType.type(type)
                    );
                    return new CharacterResponse.CharacterDetailResponse(
                            character,
                            characterImage.getImageUrl(),
                            webtoon.getTitle(),
                            characterHistoryService.isUserJoin(character.getId(), info)
                    );
                }).toList();
        return new CharacterResponse(responses);
    }

    @Transactional(readOnly = true)
    public CharacterDetailResponse getCharacterDetail(Long characterId, String type, AuthInfo info) {
        var character = characterService.findById(characterId);
        var characterImage = characterImageService.findByCharacterIdAndType(
                characterId,
                CharacterImageType.of(type)
        );
        var webtoon = webtoonService.findPreviewWebtoon(character.getWebtoonId());

        return new CharacterDetailResponse(
                character,
                characterService.findRank(characterId),
                characterImage.getImageUrl(),
                webtoon,
                characterHistoryService.isUserJoin(characterId, info),
                characterHistoryService.countJoined(characterId)
        );
    }
}
