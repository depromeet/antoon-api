package kr.co.antoon.error.exception.oauth;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsOauthInfoException extends BusinessException {
    public NotExistsOauthInfoException() {
        super(ErrorMessage.NOT_EXISTS_OAUTH_INFO);
    }
}
