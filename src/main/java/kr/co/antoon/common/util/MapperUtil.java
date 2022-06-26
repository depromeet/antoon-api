package kr.co.antoon.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.MapperJsonException;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class MapperUtil {
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

    /**
     * @return String
     * @apiNote object mapper write
     **/
    public static <T> String write(T data) {
        try {
            return mapper().writeValueAsString(data);
        } catch (JsonProcessingException exception) {
            throw new MapperJsonException(ErrorMessage.MAPPER_JSON_ERROR);
        }
    }
}
