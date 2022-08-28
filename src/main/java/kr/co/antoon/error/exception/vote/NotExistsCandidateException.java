package kr.co.antoon.error.exception.vote;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsCandidateException extends BusinessException {
    public NotExistsCandidateException() {
        super(ErrorMessage.NOT_EXISTS_CANDIDATE);
    }
}
