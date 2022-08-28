package kr.co.antoon.error.exception.user;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class InvalidUserRoleException extends BusinessException {
    public InvalidUserRoleException() {
        super(ErrorMessage.NOT_VALID_ROLE_ERROR);
    }
}
