package com.example.online_shop.config.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${aws-access-key}")
    private String AWS_ACCESS_KEY;
    @Value("${aws-secret-key}")
    private String AWS_SECRET_KEY;
    @Value("${aws-region}")
    private String AWS_REGION;

    @Bean
    S3Client s3Client() {

        Region region = Region.of(AWS_REGION);

        final AwsBasicCredentials credentials = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
