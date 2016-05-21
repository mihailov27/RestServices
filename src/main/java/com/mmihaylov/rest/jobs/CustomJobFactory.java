package com.mmihaylov.rest.jobs;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.inject.Provider;

/**
 * Factory for creating jobs.
 */
public class CustomJobFactory implements JobFactory {

    private static final Logger LOG = LogManager.getLogger(CustomJobFactory.class);

    Provider<IndexJob> indexJobProvider;

    @Inject
    public CustomJobFactory(Provider<IndexJob> indexJobProvider) {
        LOG.debug("Creating instance of job factory.");
        this.indexJobProvider = indexJobProvider;
    }

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = triggerFiredBundle.getJobDetail();
        Class jobClass = jobDetail.getJobClass();
        Job job = getJobInstance(jobClass);
        return job;
    }

    private Job getJobInstance(Class jobClass) {
        LOG.debug("Creating job instance of: %s", jobClass);
        Job job = null;
        if(IndexJob.class.equals(jobClass)) {
            job = indexJobProvider.get();
        }
        return job;
    }
}
