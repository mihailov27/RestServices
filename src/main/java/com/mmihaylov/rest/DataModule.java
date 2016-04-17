package com.mmihaylov.rest;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mmihaylov.rest.database.dao.NewsDao;
import com.mmihaylov.rest.database.dao.NewsDaoImpl;

/**
 * binding data.
 */
public class DataModule extends AbstractModule {

    @Override
    protected void configure() {
        JpaPersistModule newsDbModule = new JpaPersistModule("newsDb");
        install(newsDbModule);
        bind(NewsDao.class).to(NewsDaoImpl.class);
    }
}
