package com.mmihaylov.rest.entries;

import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.services.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news")
public class NewsResource {

    private static final Logger LOGGER = LogManager.getLogger(NewsResource.class);

    private NewsService newsService;

    @Inject
    public NewsResource(NewsService newsService) {
        LOGGER.info("Initialize NewsResource.");
        this.newsService = newsService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get(@PathParam("id") Integer id) throws RestServicesException {
        LOGGER.info("Get news with id: %d", id);
        try {
            String newsText = newsService.getNews(id);
            return Response.status(Response.Status.OK)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(newsText)
                    .build();
        } catch (RestServicesException rse) {
            LOGGER.error("Failure in server side. ", rse);
            throw rse;
            //return "Failure in server side. " + rse.getMessage();
            //Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failure in server side.").build();
        }
        //return Response.status(Response.Status.OK).entity(newsText).build();
    }
}