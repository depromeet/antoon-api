package kr.co.antoon.error.exception.oauth;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class TokenInvalidException extends BusinessException {
    public TokenInvalidException() {
        super(ErrorMessage.NOT_VALIDATE_TOKEN);
    }
}
