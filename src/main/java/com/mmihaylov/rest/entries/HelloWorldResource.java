package com.mmihaylov.rest.entries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/plain-text-service")
public class HelloWorldResource {

    private static final Logger LOGGER = LogManager.getLogger(HelloWorldResource.class);

    @GET
    @Path("/hello-world")
    public Response helloWorld() {
        LOGGER.info("Execute GET request hello world.");
        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN)
                .entity("Hello world").build();
    }
}
