package com.mmihaylov.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mmihaylov.rest.resources.HelloWorldResource;
import com.mmihaylov.rest.resources.NewsResource;
import com.mmihaylov.rest.resources.UserResource;

/**
 * rest resources.
 */
public class ResourcesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloWorldResource.class).in(Singleton.class);
        bind(NewsResource.class).in(Singleton.class);
        bind(UserResource.class).in(Singleton.class);
    }
}
