package com.mmihaylov.rest;

import com.google.inject.Singleton;
import com.mmihaylov.rest.database.DatabaseSetup;
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
        bindDatabase();
        bindServices();
        bindResources();
    }

    protected void bindDatabase() {
        bind(DatabaseSetup.class).asEagerSingleton();
    }

    protected void bindResources() {
        bind(HelloWorldResource.class).in(Singleton.class);
        bind(NewsResource.class).in(Singleton.class);
    }

    protected void bindServices() {
        bind(NewsService.class).to(NewsServiceImpl.class).in(Singleton.class);
    }
}
