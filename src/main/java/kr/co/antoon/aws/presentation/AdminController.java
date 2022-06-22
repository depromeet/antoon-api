package kr.co.antoon.aws.presentation;

import kr.co.antoon.aws.application.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/admin", produces = APPLICATION_JSON_UTF_8)
public class AdminController {
    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public String test(@RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadImageToS3("test",multipartFile);
    }
}
