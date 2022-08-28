package kr.co.antoon.error.exception.oauth;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class TokenExpiredException extends BusinessException {
    public TokenExpiredException() {
        super(ErrorMessage.EXPIRED_TOKEN);
    }
}
