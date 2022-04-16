package kr.co.antoon.user.exception;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotExistException extends BusinessException {
    public UserNotExistException(ErrorMessage errorMessage) {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}
