package kr.co.antoon.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class CommonUtil {
    public final static String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";

    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || text.isEmpty() || text.isBlank();
    }

    public static boolean isNotEmpty(String text) {
        return !Objects.isNull(text) && !text.isEmpty() && !text.isBlank();
    }

    public static ObjectMapper mapper() {
        var mapper = new ObjectMapper();

        mapper
                .setSerializationInclusion(NON_NULL);

        mapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        mapper
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
