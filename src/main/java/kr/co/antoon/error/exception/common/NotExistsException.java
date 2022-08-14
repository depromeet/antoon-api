package kr.co.antoon.error.exception.common;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsException extends BusinessException {
    public NotExistsException(ErrorMessage message) {
        super(message);
    }
}
