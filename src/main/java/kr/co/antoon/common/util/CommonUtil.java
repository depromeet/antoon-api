package kr.co.antoon.common.util;

import java.util.Objects;

/**
 * CommonUtil
 *
 * @apiNote Common Util
 **/
public class CommonUtil {
    public final static String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";

    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || text.isEmpty() || text.isBlank();
    }

    public static boolean isNotEmpty(String text) {
        return !Objects.isNull(text) && !text.isEmpty() && !text.isBlank();
    }
}
