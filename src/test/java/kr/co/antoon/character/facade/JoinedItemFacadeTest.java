package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterHistoryService;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.dto.response.PersonaResponse;
import kr.co.antoon.character.dto.response.CoupleResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class JoinedItemFacadeTest {
    @Mock
    private CharacterService characterService;

    @Mock
    private CharacterHistoryService characterHistoryService;

    @Mock
    private WebtoonService webtoonService;

    @InjectMocks
    private CharacterFacade characterFacade;

    private final Long USER_ID = 1L;

    @Test
    public void 인물_TOP30_조회() {
        // given
        var personas = new ArrayList<Character>();
        for(int i = 0; i < 30; i++) {
            personas.add(Character.builder()
                    .name("디프만 1번 출구")
                    .thumbnail("test.png")
                    .type(CharacterType.PERSONA)
                    .webtoonId(1L)
                    .build()
            );
        }

        var webtoon = Webtoon.builder()
                .title("테스트")
                .content("테스트")
                .webtoonUrl("http://테스트")
                .thumbnail("테스트.png")
                .platform(Platform.KAKAO)
                .build();

        Mockito.when(characterService.getSubjectsByTopUpper(CharacterType.PERSONA))
                .thenReturn(personas);

        Mockito.when(webtoonService.findById(anyLong()))
                .thenReturn(webtoon);

        Mockito.when(characterHistoryService.isUserJoin(any(), anyLong()))
                .thenReturn(false);

        var responses = new ArrayList<PersonaResponse.CharacterDetailResponse>();
        for(int i = 0; i < 30; i++) {
            responses.add(new PersonaResponse.CharacterDetailResponse(
                    "디프만 1번 출구",
                    "테스트",
                    "test.png",
                    0L,
                    false
            ));
        }
        var expected = new PersonaResponse(responses);
        // when
        var actual = characterFacade.getPersonasByTopUpper(USER_ID);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 커플_TOP30_조회() {
        // given
        var couples = new ArrayList<Character>();
        var responses = new ArrayList<CoupleResponse.CoupleDetailResponse>();
        for(int i = 0; i < 30; i++) {
            var couple = Character.builder()
                    .name("디프만 1번 출구,디프만 2번 출구")
                    .thumbnail("test1.png,test2.png")
                    .type(CharacterType.COUPLE)
                    .webtoonId(1L)
                    .build();
            couples.add(couple);
            responses.add(new CoupleResponse.CoupleDetailResponse(
                    couple,
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

        Mockito.when(characterService.getSubjectsByTopUpper(CharacterType.COUPLE))
                .thenReturn(couples);

        Mockito.when(webtoonService.findById(anyLong()))
                .thenReturn(webtoon);

        Mockito.when(characterHistoryService.isUserJoin(any(), anyLong()))
                .thenReturn(false);

        var expected = new CoupleResponse(responses);
        // when
        var actual = characterFacade.getCouplesByTopUpper(USER_ID);
        // then
        assertEquals(expected, actual);
    }
}
