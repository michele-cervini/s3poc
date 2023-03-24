package com.mic.s3poc.resource;

import com.mic.s3poc.facade.S3Facade;
import com.mic.s3poc.qualifiers.AWS;
import com.mic.s3poc.resource.S3ApiResource;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/aws")
public class AWSApiResource extends S3ApiResource {

    @Inject
    @AWS
    S3Facade s3Facade;

    @Override
    public S3Facade getS3Facade() {
        return s3Facade;
    }
}