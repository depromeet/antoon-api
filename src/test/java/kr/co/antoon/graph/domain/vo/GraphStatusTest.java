package kr.co.antoon.graph.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphStatusTest {
    @ParameterizedTest
    @CsvSource({
            "1, UP",
            "-1, DOWN",
            "0, MAINTAIN",
            "10000, UP",
            "-1111, DOWN",
            "-0.0001, DOWN"
    })
    void 데이터에_따라_상태값_제공(double data, GraphStatus expected) {
        GraphStatus actual = GraphStatus.of(data);

        assertEquals(expected, actual);
    }
}