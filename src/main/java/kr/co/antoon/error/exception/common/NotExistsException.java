package kr.co.antoon.error.exception.common;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class NotExistsException extends BusinessException {
    public NotExistsException(ErrorMessage message, HttpStatus status) {
        super(message, status);
    }
}