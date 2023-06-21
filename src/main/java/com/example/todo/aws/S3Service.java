package com.example.todo.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class S3Service {

    private S3Client s3;

    @Value("${aws.credentials.accessKey}")
    private String accessKey;

    @Value("${aws.credentials.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.bucketName}")
    private String bucketName;

    @PostConstruct
    private void initializeAmazon() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3 = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    // 버킷에 업로드 후 업로드 url정보 리턴
    public String uploadToS3Bucket(byte[] uploadFile, String uniqueFileName) {
        // 요청 객체를 생성
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)  // 여기에 버킷 이름을 입력
                .key(uniqueFileName)
                .build();

        // 파일을 S3에 업로드
        s3.putObject(objectRequest, RequestBody.fromBytes(uploadFile));

        // 파일이 저장된 S3 URL을 반환
        // 업로드된 파일의 URL 생성

        return s3.utilities()
                .getUrl(builder -> builder.bucket(bucketName).key(uniqueFileName))
                .toString();
    }

}
