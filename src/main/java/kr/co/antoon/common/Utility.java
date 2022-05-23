package kr.co.antoon.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static String now(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static double getDifferencePercentage(int graphScore, int scoreGap) {
        return scoreGap * 1.0 / graphScore * 100.0;
    }

    public final static String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";
}