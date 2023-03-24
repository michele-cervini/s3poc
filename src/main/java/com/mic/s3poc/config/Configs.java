package com.mic.s3poc.config;

import com.mic.s3poc.facade.AWSS3Facade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@ApplicationScoped
public class Configs {

    private static final Logger logger = LoggerFactory.getLogger(Configs.class);

    private final Properties props = new Properties();

    public Configs() {

        String envPropertiesLocation = "/opt/s3poc";
        logger.info("Default env properties location: {}", envPropertiesLocation);

        try {
            InputStream appPropertiesInputStream = AWSS3Facade.class.getClassLoader().getResourceAsStream("/application.properties");
            final Properties prop = new Properties();
            prop.load(appPropertiesInputStream);
            envPropertiesLocation = prop.get("env.properties.location").toString();

        } catch (IOException e) {
            logger.error("Error loading env properties, using default location: {}", envPropertiesLocation);
            e.printStackTrace();
        }

        try {
            logger.info("Loading env properties from: {}", envPropertiesLocation);
            InputStream envPropertiesInputStream = Files.newInputStream(Paths.get(envPropertiesLocation + "/env.properties"));
            props.load(envPropertiesInputStream);

        } catch (IOException e) {
            logger.error("Error loading env properties from: {}", envPropertiesLocation);
            e.printStackTrace();
        }
    }

    public String get(final String configKey) {
        return props.getProperty(configKey);
    }

}
