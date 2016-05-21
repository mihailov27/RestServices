package com.mmihaylov.rest.jobs;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import java.util.*;
import java.util.Calendar;


public class JobsInitializer {

    private static final Logger LOG = LogManager.getLogger(JobsInitializer.class);

    private Scheduler scheduler;

    private JobFactory customJobFactory;

    @Inject
    public JobsInitializer(SchedulerFactory schedulerFactory, JobFactory customJobFactory) throws SchedulerException  {
        this.customJobFactory = customJobFactory;
        scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(this.customJobFactory);
        scheduler.start();
        initIndexJob();
    }

    private void initIndexJob() {
        try {
            JobDetail indexJobDetail = JobBuilder
                    .newJob(IndexJob.class)
                    .withIdentity("indexJob", "indexGroup")
                    .build();
            //Calendar calendar = java.util.Calendar.getInstance();
            //calendar.add(Calendar.MINUTE, 2);
            //Date triggerDate = calendar.getTime();
            // Trigger the job to run now, and then repeat every 5 minutes
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("indexTrigger", "indexGroup")
                            //.startAt(triggerDate)
                    .startNow()
                            //.forJob(indexJobDetail)

                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(2).repeatForever())
                    .build();

            scheduler.scheduleJob(indexJobDetail, trigger);
        } catch (SchedulerException se) {
            LOG.error("Failed to init index job:", se);
        }

    }
}
