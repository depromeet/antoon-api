package kr.co.antoon.webtoon.domain;

import org.junit.jupiter.api.Test;

import static kr.co.antoon.common.util.CommonUtil.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebtoonTest {
    @Test
    void 웹툰_수정_비어있는_값_검증() {

        String content_1 = "sadasd";
        String content_2 = "";
        String content_3 = "      ";
        String content_4 = null;

        assertTrue(isNotEmpty(content_1));
        assertFalse(isNotEmpty(content_2));
        assertFalse(isNotEmpty(content_3));
        assertFalse(isNotEmpty(content_4));
    }
}