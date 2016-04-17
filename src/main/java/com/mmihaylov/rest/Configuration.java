package com.mmihaylov.rest;

import com.google.inject.persist.PersistFilter;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class Configuration extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceContainer.class);
        filter("/*").through(PersistFilter.class);
        install(new DataModule());
        install(new ServicesModule());
        install(new ResourcesModule());
    }
}
