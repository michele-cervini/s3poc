package com.mic.s3poc.facade;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public abstract class S3Facade {

    private static final Logger logger = LoggerFactory.getLogger(S3Facade.class);

    private String url;
    private String accessKey;
    private String secretKey;
    protected MinioClient minioClient;
    private String BUCKET;
    private String LOCAL_DIR;

    protected abstract void configureClient();

    protected void setUrl(String url) {
        this.url = url;
    }

    protected void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    protected void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    protected void setBucket(String bucket) {
        this.BUCKET = bucket;
    }

    protected void setLocalDir(String localDir) {
        this.LOCAL_DIR = localDir;
    }

    @PostConstruct
    public void initializeClient() {

        this.configureClient();

        this.minioClient =
                MinioClient.builder()
                        .endpoint(this.url)
                        .credentials(this.accessKey, this.secretKey)
                        .build();
    }

    public String uploadObject(String objectName, byte[] content) {

        String returnValue;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content);

            if(objectName == null || "".equals(objectName)) {
                objectName = UUID.randomUUID().toString();
            }

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(BUCKET).object(objectName).stream(
                                    inputStream, inputStream.available(), -1)
                            .build());
            returnValue = "Object " + objectName + " uploaded successfully";

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error uploading object", e);
            returnValue = e.getMessage();
        }
        return returnValue;
    }

    public String downloadObject(String objectName) {

        String returnValue;
        try {

            InputStream inputStream =
                    minioClient.getObject(
                            GetObjectArgs.builder().bucket(BUCKET).object(objectName).build());

            final String fileName = LOCAL_DIR + "/" + objectName.replaceAll("/", "_");
            logger.info("Downloading object {} in file: {}", objectName, fileName);
            Path path = Paths.get(fileName);
            if(!Files.exists(path)) {
                Files.createFile(path);
            }
            File targetFile = new File(fileName);
            targetFile.createNewFile();
            OutputStream outStream = new FileOutputStream(targetFile);

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            logger.info("Download completed: {}", fileName);
            returnValue = "Object downloaded in: " + fileName;

        } catch (Exception e) {
            logger.error("Error downloading object", e);
            returnValue = e.getMessage();
        }
        return returnValue;
    }

    public String deleteObject(String objectName) {

        String returnValue;
        try {
            logger.info("Removing object {} ", objectName);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(BUCKET)
                            .object(objectName)
                            .build());
            returnValue = "Object deleted";

        } catch (Exception e) {
            logger.error("Error deleting object", e);
            returnValue = e.getMessage();
        }
        return returnValue;
    }

}
