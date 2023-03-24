package com.mic.s3poc.facade;

import com.mic.s3poc.config.Configs;
import com.mic.s3poc.qualifiers.Swisscom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Swisscom
public class SwisscomS3Facade extends S3Facade {

    private static final Logger logger = LoggerFactory.getLogger(SwisscomS3Facade.class);

    @Inject
    private Configs configs;

    @Override
    public void configureClient() {

        final String url = configs.get("swisscom.url");
        logger.info("Url: {}", url);
        setUrl(url);

        final String accessKey = configs.get("swisscom.access.key");
        logger.info("Access key: {}", accessKey);
        setAccessKey(accessKey);

        final String secretKey = configs.get("swisscom.secret.key");
        //logger.info("Secret key: {}", secretKey);
        setSecretKey(secretKey);

        final String bucket = configs.get("swisscom.bucket");
        logger.info("Bucket: {}", bucket);
        setBucket(bucket);

        final String localDir = configs.get("swisscom.local.dir");
        logger.info("Aws local dir: {}", localDir);
        setLocalDir(localDir);

    }
}
