package kr.co.antoon.error.exception.common;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MapperJsonException extends BusinessException {
    public MapperJsonException(ErrorMessage message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
