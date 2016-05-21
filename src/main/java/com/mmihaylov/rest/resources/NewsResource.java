package com.mmihaylov.rest.resources;

import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.resources.model.NewsEntity;
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
public class NewsResource extends BaseResource {

    private static final Logger LOGGER = LogManager.getLogger(NewsResource.class);
    private static final String CHARSET_UTF8 = "; charset=UTF-8";

    private NewsService newsService;

    @Inject
    public NewsResource(NewsService newsService) {
        LOGGER.info("Initialize NewsResource.");
        this.newsService = newsService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public Response getNewsContent(@PathParam("id") Integer id) {
        LOGGER.info("Get news with id: %d", id);
        try {
            NewsEntity newsEntity = newsService.getNews(id);
            return Response.status(Response.Status.OK)
                    .entity(newsEntity)
                    .build();
        } catch (RestServicesException rse) {
            return commonError();
        }
    }
}