package kr.co.antoon.error.exception.oauth;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BusinessException {
    public TokenExpiredException(ErrorMessage errorMessage) {
        super(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}