package kr.co.antoon.user.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenderTest {
    @ParameterizedTest
    @CsvSource({
            "female, FEMALE",
            "male, MALE",
            "none, NONE"
    })
    void 성별_정보_만들기(String gender, Gender expected) {
        Gender actual = Gender.of(gender);

        assertEquals(expected, actual);
    }
}