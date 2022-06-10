package kr.co.antoon.common.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonUtilTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "          ",
            "",
            "  ",
            "        ",
            "       ",
            "     ",
            "      "
    })
    void 문자열_데이터가_비어_있는지_확인_성공(String text) {
        boolean result = CommonUtil.isEmpty(text);

        assertTrue(result);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
            "행 복 ",
            "행",
            "1",
            "   z     ",
            " z      ",
            "   z  ",
            " 2      "
    })
    void 문자열_데이터가_비어_있는지_확인_실패(String text) {
        boolean result = CommonUtil.isEmpty(text);

        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "행 복 ",
            "행",
            "1",
            "    z    ",
            "    z   ",
            "   z  ",
            " 2      "
    })
    void 문자열_데이터가_비어_있지_않는지_확인_성공(String text) {
        boolean result = CommonUtil.isNotEmpty(text);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "                      ",
            "",
            "",
            "   ",
            "",
            "      ",
            "      "
    })
    void 문자열_데이터가_비어_있지_않는지_확인_실패(String text) {
        boolean result = CommonUtil.isNotEmpty(text);

        assertFalse(result);
    }
}