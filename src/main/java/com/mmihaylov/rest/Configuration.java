package com.mmihaylov.rest;

import com.mmihaylov.rest.database.DataModule;
import com.mmihaylov.rest.resources.ResourcesModule;
import com.mmihaylov.rest.search.SearchModule;
import com.mmihaylov.rest.services.ServicesModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class Configuration extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceContainer.class);
        install(new DataModule());
        install(new SearchModule());
        install(new ServicesModule());
        install(new ResourcesModule());
    }
}
