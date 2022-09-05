package kr.co.antoon.webtoon.domain;

import kr.co.antoon.webtoon.domain.WebtoonStatusCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebtoonStatusCountTest {
    private WebtoonStatusCount webtoonStatusCount;

    @BeforeEach
    void setup() {
        webtoonStatusCount = new WebtoonStatusCount();
    }

    @Test
    void 카운트처리() {
        int actual = webtoonStatusCount.count();
        assertEquals(0, actual);
    }
}