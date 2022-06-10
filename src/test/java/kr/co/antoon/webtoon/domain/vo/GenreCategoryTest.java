package kr.co.antoon.webtoon.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreCategoryTest {
    @ParameterizedTest
    @CsvSource({
            "일상, EVERYDAY",
            "개그, GAG",
            "판타, NONE",
            "드라마, DRAMA"
    })
    void 데이터에_따라_상태값_제공(String data, GenreCategory expected) {
        GenreCategory actual = GenreCategory.of(data);

        assertEquals(expected, actual);
    }
}