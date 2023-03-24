package com.mic.s3poc.resource;

import com.mic.s3poc.facade.S3Facade;
import com.mic.s3poc.qualifiers.Swisscom;
import com.mic.s3poc.resource.S3ApiResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/swisscom")
public class SwisscomApiResource extends S3ApiResource {

    @Inject
    @Swisscom
    private S3Facade s3Facade;

    @Override
    public S3Facade getS3Facade() {
        return s3Facade;
    }

}