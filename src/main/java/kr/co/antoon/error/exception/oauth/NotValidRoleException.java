package kr.co.antoon.error.exception.oauth;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class NotValidRoleException extends BusinessException {
    public NotValidRoleException(ErrorMessage message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
