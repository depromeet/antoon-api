package kr.co.antoon.error.exception.aws;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.BusinessException;

public class FileUploadInvalidException extends BusinessException {
    public FileUploadInvalidException() {
        super(ErrorMessage.FILE_UPLOAD_ERROR);
    }
}
