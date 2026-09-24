package com.kbds.pay.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import org.apache.commons.lang3.time.DateFormatUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class S3Appender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private String bucketName;
    private String accessKey;
    private String secretKey;
    private S3AsyncClient s3Client;
    // 1. Define the encoder field
    private Encoder<ILoggingEvent> encoder;

    // 2. Add setter/getter for XML injection
    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }

    public Encoder<ILoggingEvent> getEncoder() {
        return encoder;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4, // core threads
            20, // max threads
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
    );

    @Override
    public void start() {
        // 3. Verify and start the encoder before the appender starts
        if (Objects.isNull(encoder)) {
            addError("No encoder set for the appender named [" + name + "].");
            return;
        }
        if (Objects.isNull(s3Client)) {
            AwsBasicCredentials awsCreds =
                    AwsBasicCredentials.create(accessKey, secretKey);

            s3Client = S3AsyncClient.builder()
                    .region(Region.AP_NORTHEAST_2)
                    .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                    .build();
        }

        encoder.start();
        super.start();
    }

    @Override
    public void stop() {
        if (encoder != null) {
            encoder.stop();
        }
        super.stop();
    }
    private static final AtomicLong counter = new AtomicLong();

    @Override
    protected void append(ILoggingEvent event) {
        byte[] byteArray = encoder.encode(event);
        String dateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + counter.incrementAndGet()%10000 + ".json";

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("data/"+ dateStr)
                .build();

        executor.submit(()->s3Client.putObject(
                request,
                AsyncRequestBody.fromBytes(byteArray)
        ).join() );
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
