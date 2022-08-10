package kr.co.antoon.error.exception;

import kr.co.antoon.error.dto.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage message, HttpStatus status) {
        super(message.getDescription());
        this.status = status;
        this.errorMessage = message;
    }
}
