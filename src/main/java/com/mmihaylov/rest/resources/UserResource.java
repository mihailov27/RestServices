package com.mmihaylov.rest.resources;

import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.resources.model.UserEntity;
import com.mmihaylov.rest.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResource {

    private static final Logger LOGGER = LogManager.getLogger(UserResource.class);

    UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        LOGGER.info("Get user with id: %d", id);
        UserEntity user = userService.getUser(id);
        return Response.status(Response.Status.OK).entity(user).build();
    }
}
