package kr.co.antoon.error;

import kr.co.antoon.error.dto.ErrorDto;
import kr.co.antoon.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDto> handleBusinessException(
            final BusinessException e,
            final HttpServletRequest request
    ) {
        log.error("BusinessException -> {} {}", e.getErrorMessage(), e.getCause());
        log.error("Request url {}", request.getRequestURL());

        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(
                        new ErrorDto(
                                e.getErrorMessage().name(),
                                e.getErrorMessage().getDescription())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e,
            final HttpServletRequest request
    ) {
        log.error("MethodArgumentNotValidException -> {} {}", e.getMessage(), e.getBindingResult());
        log.error("Request url {}", request.getRequestURL());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDto(
                                "MethodArgumentNotValidException",
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(RequestRejectedException.class)
    protected ResponseEntity<ErrorDto> handleRequestRejectedException(
            final RequestRejectedException e,
            final HttpServletRequest request
    ) {
        var errorMessage = e.getCause().toString() + "\n" + e.getLocalizedMessage() + Arrays.toString(e.getStackTrace());

        log.error("RequestRejectedException -> {} | {}", e.getMessage(), errorMessage);
        log.error("Request url {}", request.getRequestURL());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorDto(
                                e.getMessage(),
                                errorMessage
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleException(
            final Exception e,
            final HttpServletRequest request
    ) {
        var errorMessage = e.getCause().toString() + "\n" + e.getLocalizedMessage() + Arrays.toString(e.getStackTrace());

        log.error("Exception -> {} | {}", e.getMessage(), errorMessage);
        log.error("Request url {}", request.getRequestURL());

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
