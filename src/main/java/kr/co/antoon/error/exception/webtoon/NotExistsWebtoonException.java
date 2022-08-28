package kr.co.antoon.error.exception.webtoon;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class NotExistsWebtoonException extends BusinessException {
    public NotExistsWebtoonException() {
        super(ErrorMessage.NOT_EXISTS_WEBTOON_ERROR);
    }
}
