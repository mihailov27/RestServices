package com.mmihaylov.rest;

import com.google.inject.Singleton;
import com.mmihaylov.rest.entries.HelloWorldResource;
import com.mmihaylov.rest.entries.NewsResource;
import com.mmihaylov.rest.services.NewsService;
import com.mmihaylov.rest.services.impl.NewsServiceImpl;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class Configuration extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceContainer.class);
        bindServices();
        bindResources();
    }

    protected void bindResources() {
        bind(HelloWorldResource.class).in(Singleton.class);
        bind(NewsResource.class).in(Singleton.class);
    }

    protected void bindServices() {
        //bind(HTMLParser.class);
        //bind(URLReader.class);
        bind(NewsService.class).to(NewsServiceImpl.class).in(Singleton.class);
    }
}
