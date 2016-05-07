package com.mmihaylov.rest.services;

import com.mmihaylov.rest.BaseTest;
import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.resources.model.NewsEntity;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

public class NewsServiceTest extends BaseTest {

    @Inject
    NewsService newsService;

    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGetNews() throws RestServicesException{
        NewsEntity news = newsService.getNews(344187);
        Assert.assertNotNull(news);

        // test again if it is in the database
        NewsEntity news2 = newsService.getNews(344187);
        Assert.assertNotNull(news2);
    }
}
