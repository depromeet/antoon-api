package kr.co.antoon.character.domain;

import kr.co.antoon.character.domain.vo.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinedItemTest {
    private Character character;

    @BeforeEach
    void setSubject() {
        character = Character.builder()
                .name("디프만 1번 출구")
                .thumbnail("test.png")
                .type(CharacterType.PERSONA)
                .webtoonId(1L)
                .build();
    }

    @Test
    void 인물_관계_코인_업데이트() {
        character.amountUpdate(100L);
        assertEquals(100L, character.getCoinAmount());
    }
}
