package kr.co.antoon.feedback.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreTest {
    @ParameterizedTest
    @CsvSource({
            "1, ONE_STAR",
            "2, TWO_STAR",
            "3, THREE_STAR",
            "4, FOUR_STAR",
            "5, FIVE_STAR",
            "6, THREE_STAR",
            "0, THREE_STAR",
            "-1, THREE_STAR"
    })
    void 피드백_점수_생성(int weight, Score expected) {
        Score actual = Score.of(weight);

        assertEquals(expected, actual);
    }
}