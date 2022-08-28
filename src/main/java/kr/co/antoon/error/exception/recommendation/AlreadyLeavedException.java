package kr.co.antoon.error.exception.recommendation;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class AlreadyLeavedException extends BusinessException {
    public AlreadyLeavedException() {
        super(ErrorMessage.ALREADY_LEAVED_ERROR);
    }
}
