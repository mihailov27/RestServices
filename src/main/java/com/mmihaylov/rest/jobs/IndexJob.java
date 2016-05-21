package com.mmihaylov.rest.jobs;


import com.google.inject.Inject;
import com.mmihaylov.rest.database.dao.NewsDao;
import com.mmihaylov.rest.database.entities.News;
import com.mmihaylov.rest.search.indexer.Indexer;
import com.mmihaylov.rest.search.indexer.NewsIndexer;
import com.mmihaylov.rest.search.model.NewsIndexEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Named;
import java.util.Date;
import java.util.List;

/**
* Job for synchronizing the entities in Database and Elastic search.
*/
public class IndexJob implements Job {

    private static final Logger LOG = LogManager.getLogger(IndexJob.class);

    private NewsDao newsDao;
    private Indexer newsIndexer;

    @Inject
    public IndexJob(NewsDao newsDao, @Named("newsIndexer") Indexer newsIndexer) {
        this.newsDao = newsDao;
        this.newsIndexer = newsIndexer;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("My job is getting executed.");
        try {
            LOG.info("Index news records which are not processed yet by indexer.");
            List<News> news = newsDao.getNotIndexedNews();
            if(news == null) {
                return;
            }

            for(News newsRecord : news) {
                NewsIndexEntity newsIndexEntity = new NewsIndexEntity();
                newsIndexEntity.setCreatedDate(new Date());
                newsIndexEntity.setPublicationDate(newsRecord.getPublicationDate());
                newsIndexEntity.setContent(new String(newsRecord.getNewsText()));
                newsIndexEntity.setTitle(newsRecord.getTitle());
                newsIndexer.index(newsIndexEntity);
                newsRecord.setIsIndexed(true);
                newsDao.update(newsRecord);
            }
        } catch (Exception e) {
            LOG.error("Fail to index entities: ", e);
        }
    }
}

