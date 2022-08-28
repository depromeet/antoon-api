package kr.co.antoon.error.exception.user;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsUserException extends BusinessException {
    public NotExistsUserException() {
        super(ErrorMessage.NOT_EXIST_USER);
    }
}
