package kr.co.antoon.error.exception.vote;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsTopicException extends BusinessException {
    public NotExistsTopicException() {
        super(ErrorMessage.NOT_EXIST_TOPIC);
    }
}
