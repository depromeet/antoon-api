package kr.co.antoon.error.exception.character;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsCharacterImageException extends BusinessException {
    public NotExistsCharacterImageException() {
        super(ErrorMessage.NOT_EXISTS_CHARACTER_IMAGE);
    }
}
