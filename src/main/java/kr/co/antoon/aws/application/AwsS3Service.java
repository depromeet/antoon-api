package kr.co.antoon.aws.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jdk.jfr.Category;
import kr.co.antoon.aws.domain.vo.AntDefaultImageName;
import kr.co.antoon.aws.domain.vo.S3Category;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;

    private static final String FILE_EXTENSION_SEPARATOR = ".";

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<String> uploadImageToS3(S3Category category, List<MultipartFile> multipartFiles) {
        log.info("bucket : {}", bucketName);
        List<String> urls = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles) {
            String fileName = buildFileName(category, multipartFile.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new NotExistsException(ErrorMessage.FILE_UPLOAD_ERROR);
            }
            urls.add(amazonS3Client.getUrl(bucketName, fileName).toString());
        }
        return urls;
    }

    public String randomProfileImage() {
        String fileName = S3Category.ANT_DEFAULT + "/" + AntDefaultImageName.getRandomAnt()+".png";
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    public static String buildFileName(S3Category category, String originalFileName) {
        int fileExtenstionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtenstionIndex);
        String fileName = originalFileName.substring(0, fileExtenstionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return category+"/"+fileName+"-"+now+fileExtension;
    }

}