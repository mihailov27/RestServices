package com.mmihaylov.rest;

import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mmihaylov.rest.database.dao.NewsDao;
import com.mmihaylov.rest.database.dao.NewsDaoImpl;
import com.mmihaylov.rest.resources.HelloWorldResource;
import com.mmihaylov.rest.resources.NewsResource;
import com.mmihaylov.rest.services.NewsService;
import com.mmihaylov.rest.services.impl.NewsServiceImpl;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class Configuration extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceContainer.class);
        filter("/*").through(PersistFilter.class);
        bindDatabase();
        bindServices();
        bindResources();
    }

    /* Resources binding */
    protected void bindResources() {
        bind(HelloWorldResource.class).in(Singleton.class);
        bind(NewsResource.class).in(Singleton.class);
    }

    /* Services binding */
    protected void bindServices() {
        bind(NewsService.class).to(NewsServiceImpl.class).in(Singleton.class);
    }

    /*  Database binding */
    protected void bindDatabase() {
        JpaPersistModule newsDbModule = new JpaPersistModule("newsDb");
        install(newsDbModule);
        bind(NewsDao.class).to(NewsDaoImpl.class);
    }
}
