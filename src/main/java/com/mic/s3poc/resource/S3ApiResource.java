package com.mic.s3poc.resource;

import com.mic.s3poc.facade.S3Facade;
import com.mic.s3poc.resource.dto.UploadRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Base64;

public abstract class S3ApiResource {

    protected abstract S3Facade getS3Facade();

    @GET
    @Path("/test")
    @Produces("text/plain")
    public String test() {
        return "I'm working with: " + getS3Facade().getClass().getSimpleName();
    }

    @GET
    @Path("/{objectName}")
    @Produces("text/plain")
    public String download(@PathParam("objectName") String objectName) {
        return getS3Facade().downloadObject(objectName);
    }

    @PUT
    @Produces("text/plain")
    @Consumes("application/json")
    public String upload(UploadRequest uploadRequest) {
        if (uploadRequest.getContent() == null) {
            return "Please provide a non empty obejct";
        }
        byte[] content = Base64.getDecoder().decode(uploadRequest.getContent());
        return getS3Facade().uploadObject(uploadRequest.getObjectName(), content);
    }

    @DELETE
    @Path("/{objectName}")
    @Produces("text/plain")
    public String delete(@PathParam("objectName") String objectName) {
        return getS3Facade().deleteObject(objectName);
    }

}
