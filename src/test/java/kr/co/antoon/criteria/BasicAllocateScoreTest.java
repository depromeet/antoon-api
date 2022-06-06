package kr.co.antoon.criteria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicAllocateScoreTest {
    private BasicAllocateScore basicAllocateScore;

    @BeforeEach
    void 초기화l() {
        basicAllocateScore = new BasicAllocateScore();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "9.94",
            "9.99",
            "9",
            "4",
            "9.2"
    })
    void 웹툰_점수_변환_500점을_초과_할_수_없음(String score) {
        int actual = basicAllocateScore.webtoonScore(Double.parseDouble(score));

        int expected = 500;

        assertTrue(actual <= expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "10",
            "9",
            "8",
            "7",
            "6",
            "5",
            "4",
            "3",
            "2",
            "1"
    })
    void 토론_점수_합은_200점을_초과할_수_없음(String score) {
        int actual = basicAllocateScore.discussionScore(Long.parseLong(score));

        int expected = 200;

        assertTrue(actual <= expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "10",
            "9",
            "8",
            "7",
            "6",
            "5",
            "4",
            "3",
            "2",
            "1"
    })
    void 추천_점수_합은_300점을_초과할_수_없음(String score) {
        int actual = basicAllocateScore.recommendationScore(Integer.parseInt(score));

        int expected = 300;

        assertTrue(actual <= expected);
    }

    @ParameterizedTest
    @CsvSource({
            "100, 200, 200",
            "100, 100, 100",
            "0, 100, 100",
            "0, 0, 0"
    })
    void 그래프_점수는_최소_250점을_넘는다(int discussionScore, int recommendationScore, int webtoonScore) {
        int actual = basicAllocateScore.graphScore(discussionScore, recommendationScore, webtoonScore);

        assertTrue((actual >= 250));
    }

    @ParameterizedTest
    @CsvSource({
            "100, 200, 200.0",
            "300, 200, 66.7",
            "400, 400, 100.0"
    })
    void 그래프_스코어_차이_퍼센트지를_구한다(int graphScore, int scoreGap, double expected) {
        double actual = basicAllocateScore.getDifferencePercentage(graphScore, scoreGap);

        assertEquals(expected, actual);
    }
}