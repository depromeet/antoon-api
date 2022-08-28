package kr.co.antoon.error.exception.coin;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsCoinWalletException extends BusinessException {
    public NotExistsCoinWalletException() {
        super(ErrorMessage.NOT_EXISTS_ANT_COIN_WALLET);
    }
}
