package kr.co.antoon.error.exception.vote;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class AlreadyExistsVoteException extends BusinessException {
    public AlreadyExistsVoteException() {
        super(ErrorMessage.ALREADY_VOTE_ERROR);
    }
}
