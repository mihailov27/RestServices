package com.mmihaylov.rest.services.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.database.dao.NewsDao;
import com.mmihaylov.rest.database.entities.News;
import com.mmihaylov.rest.html.NoviniParser;
import com.mmihaylov.rest.resources.model.NewsEntity;
import com.mmihaylov.rest.services.NewsService;
import com.mmihaylov.rest.utils.NewsConverter;
import com.mmihaylov.rest.utils.URLReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class NewsServiceImpl implements NewsService {

    private static final Logger LOG = LogManager.getLogger(NewsServiceImpl.class);

    private static final String NOVINI_TEMPLATE = "http://www.novini.bg/news/%d.html";

    private URLReader urlReader;

    private NoviniParser noviniParser;

    private NewsDao newsDao;

    private NewsConverter newsConverter;

    private LoadingCache<Long, NewsEntity> cache;

    @Inject
    public NewsServiceImpl(URLReader urlReader, NoviniParser noviniParser, NewsDao newsDao,
                           NewsConverter newsConverter) {
        LOG.debug("Init News service.");
        this.urlReader = urlReader;
        this.noviniParser = noviniParser;
        this.newsDao = newsDao;
        this.newsConverter = newsConverter;
    }

    @Inject
    public void setUp() {
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(40)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, NewsEntity>() {
                    @Override
                    public NewsEntity load(Long aLong) throws Exception {
                        return NewsServiceImpl.this.loadNews(aLong);
                    }
                });
    }

    private NewsEntity loadFromHtml(long id) throws RestServicesException {
        String fullAddress = String.format(NOVINI_TEMPLATE, id);
        String htmlPage = urlReader.readURLAsString(fullAddress);
        NewsEntity newsEntity = noviniParser.parse(htmlPage);
        return newsEntity;
    }

    private News saveInDb(NewsEntity newsEntity) {
        LOG.debug("Saving in database news entity:", newsEntity);
        News news = newsConverter.revert(newsEntity);
        newsDao.persist(news);
        return news;
    }

    protected NewsEntity loadNews(long id) throws RestServicesException {
        News news = newsDao.findByExternalId(id);
        if(news == null) {
            NewsEntity newsEntity = loadFromHtml(id);
            newsEntity.setId(id);
            saveInDb(newsEntity);
            return newsEntity;
        } else {
            return null;
        }
    }

    public NewsEntity getNews(long id) throws RestServicesException {
        try {
            NewsEntity news = cache.get(id);
            return news;
        } catch (Exception eE) {
            throw new RestServicesException("Fail to load news with id: " + id, eE);
        }
    }
}