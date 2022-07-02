package kr.co.antoon.admin.presentation;

import kr.co.antoon.aws.application.AwsS3Service;
import kr.co.antoon.aws.domain.vo.S3Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/admin", produces = APPLICATION_JSON_UTF_8)
public class AdminController {
    private final AwsS3Service awsS3Service;

    // TODO : 해당 위치에 있는게 어색하네요
    @PostMapping("/upload")
    public List<String> test(@RequestPart(value = "file") List<MultipartFile> multipartFiles) {
        return awsS3Service.uploadImageToS3(S3Category.TEST, multipartFiles);
    }
}
