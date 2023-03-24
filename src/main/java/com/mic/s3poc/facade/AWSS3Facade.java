package com.mic.s3poc.facade;

import com.mic.s3poc.config.Configs;
import com.mic.s3poc.qualifiers.AWS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@AWS
public class AWSS3Facade extends S3Facade {

    private static final Logger logger = LoggerFactory.getLogger(AWSS3Facade.class);

    @Inject
    private Configs configs;

    @Override
    public void configureClient() {

        final String url = configs.get("aws.url");
        logger.info("Url: {}", url);
        setUrl(url);

        final String accessKey = configs.get("aws.access.key");
        logger.info("Access key: {}", accessKey);
        setAccessKey(accessKey);

        final String secretKey = configs.get("aws.secret.key");
        //logger.info("Secret key: {}", secretKey);
        setSecretKey(secretKey);

        final String bucket = configs.get("aws.bucket");
        logger.info("Bucket: {}", bucket);
        setBucket(bucket);

        final String localDir = configs.get("aws.local.dir");
        logger.info("Aws local dir: {}", localDir);
        setLocalDir(localDir);

    }
}
