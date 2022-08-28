package kr.co.antoon.error.exception.common;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class MapperJsonException extends BusinessException {
    public MapperJsonException() {
        super(ErrorMessage.MAPPER_JSON_ERROR);
    }
}
