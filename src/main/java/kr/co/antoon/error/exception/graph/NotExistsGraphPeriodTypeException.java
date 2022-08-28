package kr.co.antoon.error.exception.graph;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsGraphPeriodTypeException extends BusinessException {
    public NotExistsGraphPeriodTypeException() {
        super(ErrorMessage.NOT_EXISTS_PERIOD_TYPE);
    }
}
