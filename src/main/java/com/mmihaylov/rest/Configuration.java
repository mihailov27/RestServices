package com.mmihaylov.rest;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class Configuration extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceContainer.class);
        install(new DataModule());
        install(new ServicesModule());
        install(new ResourcesModule());
    }
}
