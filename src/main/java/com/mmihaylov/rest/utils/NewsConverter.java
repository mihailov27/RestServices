package com.mmihaylov.rest.utils;

import com.mmihaylov.rest.database.entities.News;
import com.mmihaylov.rest.resources.model.NewsEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewsConverter implements Converter<News, NewsEntity> {

    private static final Logger LOG = LogManager.getLogger(NewsConverter.class);

    public NewsEntity convert(News input) {
        if(input == null) {
            LOG.warn("Input is null. Return result is null too.");
            return null;
        }
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(input.getNewsExternalId());
        newsEntity.setTitle(input.getTitle());
        newsEntity.setPublishedDate(input.getPublicationDate());
        // content
        byte[] content = input.getNewsText();
        newsEntity.setContent(new String(content));
        return newsEntity;
    }

    public News revert(NewsEntity result) {
        if(result == null) {
            LOG.warn("Convert revert to resource. Result is null.");
            return null;
        }
        News news = new News();
        news.setNewsExternalId(result.getId());
        news.setTitle(result.getTitle());
        news.setPublicationDate(result.getPublishedDate());
        String content = result.getContent();
        if(CommonUtils.isNullOrEmpty(content)) {
            throw new IllegalStateException("The news content is null.");
        }
        news.setNewsText(content.getBytes());
        return news;
    }
}
