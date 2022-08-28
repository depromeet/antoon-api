package kr.co.antoon.error.exception.character;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsCharacterException extends BusinessException {
    public NotExistsCharacterException() {
        super(ErrorMessage.NOT_EXISTS_CHARACTER);
    }
}
