package kr.co.antoon.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

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

    /**
     * @return ObjectMapper
     * @apiNote object mapper
     **/
    public static ObjectMapper mapper() {
        var mapper = new ObjectMapper();

        var deserializationFeature = DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
        var serializationFeature = SerializationFeature.FAIL_ON_EMPTY_BEANS;

        mapper
                .setSerializationInclusion(NON_NULL);

        mapper
                .configure(deserializationFeature, false)
                .configure(serializationFeature, false);

        mapper
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
