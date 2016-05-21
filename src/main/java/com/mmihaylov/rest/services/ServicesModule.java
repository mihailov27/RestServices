package com.mmihaylov.rest.services;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mmihaylov.rest.services.impl.NewsServiceImpl;
import com.mmihaylov.rest.services.impl.SearchServiceImpl;
import com.mmihaylov.rest.services.impl.UserServiceImpl;

public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NewsService.class).to(NewsServiceImpl.class).in(Singleton.class);
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        bind(SearchService.class).to(SearchServiceImpl.class).in(Singleton.class);
       // bind(ScheduledIndexService.class).to(ScheduledIndexServiceImpl.class).asEagerSingleton();
    }
}
