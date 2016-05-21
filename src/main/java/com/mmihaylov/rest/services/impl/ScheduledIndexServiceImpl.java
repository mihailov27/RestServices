//package com.mmihaylov.rest.services.impl;
//
//import com.google.inject.Inject;
//import com.google.inject.persist.Transactional;
//import com.mmihaylov.rest.database.dao.NewsDao;
//import com.mmihaylov.rest.database.entities.News;
//import com.mmihaylov.rest.search.indexer.Indexer;
//import com.mmihaylov.rest.search.model.NewsIndexEntity;
//import com.mmihaylov.rest.services.ScheduledIndexService;
//import com.mmihaylov.rest.utils.CommonUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.inject.Named;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//public class ScheduledIndexServiceImpl implements ScheduledIndexService {
//
//    private static final Logger LOGGER = LogManager.getLogger(ScheduledIndexServiceImpl.class);
//
//    private ScheduledExecutorService scheduledExecutorService;
//
//    private NewsDao newsDao;
//    private Indexer newsIndexer;
//
//    @Inject
//    public ScheduledIndexServiceImpl(NewsDao newsDao, @Named("newsIndexer") Indexer newsIndexer) {
//        LOGGER.info("Init scheduler service.");
//        this.newsDao = newsDao;
//        this.newsIndexer = newsIndexer;
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(new IndexNewsRunnable(), 1, 1, TimeUnit.MINUTES);
//    }
//
//    @Transactional
//    public void indexNews() {
//        try {
//            LOGGER.info("Index news records which are not processed yet by indexer.");
//            List<News> news = newsDao.getNotIndexedNews(5);
//            if(news == null) {
//                return;
//            }
//
//            for(News newsRecord : news) {
//                NewsIndexEntity newsIndexEntity = new NewsIndexEntity();
//                newsIndexEntity.setCreatedDate(new Date());
//                newsIndexEntity.setPublicationDate(newsRecord.getPublicationDate());
//                newsIndexEntity.setContent(new String(newsRecord.getNewsText()));
//                newsIndexEntity.setTitle(newsRecord.getTitle());
//                newsIndexer.index(newsIndexEntity);
//                newsRecord.setIsIndexed(true);
//                newsDao.update(newsRecord);
//            }
//        } catch (Exception e) {
//            LOGGER.error("Fail to index entities: ", e);
//        }
//        LOGGER.info("Test update");
//    }
//
//    protected class IndexNewsRunnable implements Runnable {
//
//        public void run() {
//            indexNews();
//        }
//    }
//}
