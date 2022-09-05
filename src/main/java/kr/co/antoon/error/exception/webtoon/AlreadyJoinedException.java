package kr.co.antoon.error.exception.webtoon;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class AlreadyJoinedException extends BusinessException {
    public AlreadyJoinedException() {
        super(ErrorMessage.ALREADY_JOINED_ERROR);
    }
}
