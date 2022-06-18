package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.CharacterType;
import kr.co.antoon.character.infrastructure.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {
    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterService characterService;

    private final Long JOINED_ITEM_ID = 1L;

    @Test
    public void 인물_관계_TOP30_조회() {
        // given
        var expected = new ArrayList<Character>();
        for(int i = 0; i < 30; i++) {
            expected.add(Character.builder()
                    .name("디프만 1번 출구")
                    .thumbnail("test.png")
                    .type(CharacterType.PERSONA)
                    .webtoonId(1L)
                    .build()
            );
        }

        Mockito.when(characterRepository.findTop30ByTypeOrderByAmountDesc(any()))
                .thenReturn(expected);

        // when
        List<Character> actual = characterService.getSubjectsByTopUpper(CharacterType.PERSONA);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 인물_관계_단일_조회() {
        // given
        var expected = Character.builder()
                .name("디프만 1번 출구")
                .thumbnail("test.png")
                .type(CharacterType.PERSONA)
                .webtoonId(1L)
                .build();

         Mockito.when(characterRepository.findById(anyLong()))
                 .thenReturn(Optional.of(expected));
         // when
         Character actual = characterService.findById(JOINED_ITEM_ID);
         // then
         assertEquals(expected, actual);
    }
}
