package kr.co.antoon.error;

import kr.co.antoon.error.dto.ErrorDto;
import kr.co.antoon.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDto> handleBusinessException(final BusinessException e) {
        log.error("[ERROR] BusinessException -> {} {}", e.getErrorMessage(), e.getCause());
        
        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(
                        new ErrorDto(
                                e.getErrorMessage().name(),
                                e.getErrorMessage().getDescription())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("[ERROR] MethodArgumentNotValidException -> {} {}", e.getMessage(), e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDto(
                                "MethodArgumentNotValidException",
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleException(final Exception e) {
        var errorMessage = e.getCause().toString() + "\n" + e.getLocalizedMessage() + Arrays.toString(e.getStackTrace());
        log.error("[ERROR] Exception -> {} | {}", e.getMessage(), errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDto(
                                e.getMessage(),
                                errorMessage
                        )
                );
    }
}
