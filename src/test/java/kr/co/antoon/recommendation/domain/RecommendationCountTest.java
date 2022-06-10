package kr.co.antoon.recommendation.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecommendationCountTest {
    private RecommendationCount recommendationCount;

    @BeforeEach
    void setup() {
        recommendationCount = new RecommendationCount();
    }

    @Test
    void 카운트처리() {
        int actual = recommendationCount.count();
        assertEquals(0, actual);
    }
}