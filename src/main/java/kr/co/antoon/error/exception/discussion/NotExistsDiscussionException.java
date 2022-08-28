package kr.co.antoon.error.exception.discussion;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsDiscussionException extends BusinessException {
    public NotExistsDiscussionException() {
        super(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR);
    }
}
