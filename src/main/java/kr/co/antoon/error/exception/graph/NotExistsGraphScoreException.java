package kr.co.antoon.error.exception.graph;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsGraphScoreException extends BusinessException {
    public NotExistsGraphScoreException() {
        super(ErrorMessage.NOT_EXISTS_GRAPH_SCORE_ERROR);
    }
}
