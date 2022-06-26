package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterHistoryService;
import kr.co.antoon.character.application.CharacterImageService;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.CharacterImage;
import kr.co.antoon.character.domain.vo.CharacterImageType;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.dto.response.CharacterResponse;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CharacterFacadeTest {
    @Mock
    private CharacterService characterService;

    @Mock
    private CharacterHistoryService characterHistoryService;

    @Mock
    private WebtoonService webtoonService;

    @Mock
    private CharacterImageService characterImageService;

    @InjectMocks
    private CharacterFacade characterFacade;

    private final Long USER_ID = 1L;

    @Test
    public void 인물_TOP30_조회() {
        // given
        var characters = new ArrayList<Character>();
        var responses = new ArrayList<CharacterResponse.CharacterDetailResponse>();
        for(int i = 0; i < 30; i++) {
            var character = Character.builder()
                    .name("디프만 1번 출구")
                    .type(CharacterType.PERSONA)
                    .webtoonId(1L)
                    .build();
            characters.add(character);
            responses.add(new CharacterResponse.CharacterDetailResponse(
                    character,
                    "테스트.png",
                    "테스트",
                    false
            ));
        }

        var webtoon = Webtoon.builder()
                .title("테스트")
                .content("테스트")
                .webtoonUrl("http://테스트")
                .thumbnail("테스트.png")
                .platform(Platform.KAKAO)
                .build();

        var mockCharacterImage = CharacterImage.builder()
                .characterId(1L)
                .type(CharacterImageType.PERSONA)
                .imageUrl("테스트.png")
                .build();

        AuthInfo mockInfo = new AuthInfo(USER_ID, List.of(Role.USER));

        Mockito.when(characterService.getCharactersByTopUpper(any()))
                .thenReturn(characters);

        Mockito.when(webtoonService.findById(anyLong()))
                .thenReturn(webtoon);

        Mockito.when(characterImageService.findByCharacterIdAndType(any(), any()))
                .thenReturn(mockCharacterImage);

        Mockito.when(characterHistoryService.isUserJoin(any(), any()))
                .thenReturn(false);

        var expected = new CharacterResponse(responses);
        // when
        var actual = characterFacade.getTopUpper("PERSONA", mockInfo);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 커플_TOP30_조회() {
        // given
        var couples = new ArrayList<Character>();
        var responses = new ArrayList<CharacterResponse.CharacterDetailResponse>();
        for(int i = 0; i < 30; i++) {
            var couple = Character.builder()
                    .name("디프만 1번 출구")
                    .type(CharacterType.COUPLE)
                    .webtoonId(1L)
                    .build();
            couples.add(couple);
            responses.add(new CharacterResponse.CharacterDetailResponse(
                    couple,
                    "테스트1.png,테스트2.png",
                    "테스트",
                    false
            ));
        }

        var webtoon = Webtoon.builder()
                .title("테스트")
                .content("테스트")
                .webtoonUrl("http://테스트")
                .thumbnail("테스트.png")
                .platform(Platform.KAKAO)
                .build();

        var mockCharacterImage = CharacterImage.builder()
                .characterId(1L)
                .type(CharacterImageType.COUPLE)
                .imageUrl("테스트1.png,테스트2.png")
                .build();

        AuthInfo mockInfo = new AuthInfo(USER_ID, List.of(Role.USER));

        Mockito.when(characterService.getCharactersByTopUpper(any()))
                .thenReturn(couples);

        Mockito.when(webtoonService.findById(anyLong()))
                .thenReturn(webtoon);

        Mockito.when(characterImageService.findByCharacterIdAndType(any(), any()))
                .thenReturn(mockCharacterImage);

        Mockito.when(characterHistoryService.isUserJoin(any(), any()))
                .thenReturn(false);

        var expected = new CharacterResponse(responses);
        // when
        var actual = characterFacade.getTopUpper("COUPLE",mockInfo);
        // then
        assertEquals(expected, actual);
    }
}
