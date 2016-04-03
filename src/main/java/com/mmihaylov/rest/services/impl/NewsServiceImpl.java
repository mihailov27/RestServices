package com.mmihaylov.rest.services.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.database.dao.NewsDao;
import com.mmihaylov.rest.database.entities.News;
import com.mmihaylov.rest.html.HTMLParser;
import com.mmihaylov.rest.services.NewsService;
import com.mmihaylov.rest.utils.URLReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class NewsServiceImpl implements NewsService {

    private static final Logger LOGGER = LogManager.getLogger(NewsServiceImpl.class);

    private static final String NOVINI_TEMPLATE = "http://www.novini.bg/news/%d.html";

    private URLReader urlReader;

    private HTMLParser htmlParser;

    private NewsDao newsDao;

    private LoadingCache<Long, News> cache;

    @Inject
    public NewsServiceImpl(URLReader urlReader, HTMLParser htmlParser, NewsDao newsDao) {
        this.urlReader = urlReader;
        this.htmlParser = htmlParser;
        this.newsDao = newsDao;
    }

    @Inject
    public void setUp() {
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(40)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, News>() {
                    @Override
                    public News load(Long aLong) throws Exception {
                        return NewsServiceImpl.this.loadNews(aLong);
                    }
                });
    }

    private String loadFromHtml(long id) throws RestServicesException {
        String fullAddress = String.format(NOVINI_TEMPLATE, id);
        String htmlPage = urlReader.readURLAsString(fullAddress);
        String news = htmlParser.parseNews(htmlPage);
        return news;
    }

    private News saveInDb(long externalId, String content) {
        LOGGER.debug("Saving in database external id '%d', content '%s'", externalId,
                content.length() > 50 ? content.substring(0,50) : content);
        News news = new News();
        news.setNewsExternalId(externalId);
        news.setCreated(new Date());
        news.setNewsText(content.getBytes());
        newsDao.persist(news);
        return news;
    }

    private News loadNews(long id) throws RestServicesException {
        News news = newsDao.findByExternalId(id);
        if(news == null) {
            String content = loadFromHtml(id);
            news = saveInDb(id, content);
        }
        return news;
    }

    public String getNews(long id) throws RestServicesException {
        String contentNews = null;
        try {
            News news = cache.get(id);
            contentNews =  new String(news.getNewsText());
        } catch (ExecutionException eE) {

        }
        return contentNews;
    }
}
