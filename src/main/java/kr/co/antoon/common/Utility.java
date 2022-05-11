package kr.co.antoon.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static String now(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}