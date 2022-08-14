package kr.co.antoon.error.exception.common;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class AlreadyExistsException extends BusinessException {
    public AlreadyExistsException(ErrorMessage message) {
        super(message);
    }
}
