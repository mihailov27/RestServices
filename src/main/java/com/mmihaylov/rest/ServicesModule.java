package com.mmihaylov.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mmihaylov.rest.services.NewsService;
import com.mmihaylov.rest.services.UserService;
import com.mmihaylov.rest.services.impl.NewsServiceImpl;
import com.mmihaylov.rest.services.impl.UserServiceImpl;

/**
 * bind services
 */
public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NewsService.class).to(NewsServiceImpl.class).in(Singleton.class);
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
    }
}
