package com.mmihaylov.rest.resources;

import com.mmihaylov.rest.resources.model.ErrorEntity;

import javax.ws.rs.core.Response;

public class BaseResource {

    private static final String ERROR_MSG
            = "After a server error, you request cannot be processed. Please excuse us.";

    protected Response commonError() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ERROR_MSG).build();
    }

    protected Response error(ErrorEntity errorEntity) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorEntity).build();
    }
}
