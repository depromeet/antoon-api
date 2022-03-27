package kr.co.toonzip.error;

import kr.co.toonzip.error.dto.ErrorDto;
import kr.co.toonzip.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDto> handleBusinessException(
            final BusinessException e) {
        log.error("[ERROR] Exception -> {}", e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorDto(e.getMessage(), e.getCause().toString()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleException(final Exception e) {
        log.error("[ERROR] Exception -> {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(e.getMessage(), e.getCause().toString()));
    }
}