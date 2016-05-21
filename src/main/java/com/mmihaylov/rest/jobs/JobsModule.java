package com.mmihaylov.rest.jobs;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

public class JobsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JobsInitializer.class).asEagerSingleton();
        bind(SchedulerFactory.class).to(StdSchedulerFactory.class).in(Singleton.class);
        bind(JobFactory.class).to(CustomJobFactory.class).in(Singleton.class);
    }
}
