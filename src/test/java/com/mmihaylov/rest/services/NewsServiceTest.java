package com.mmihaylov.rest.services;

import com.mmihaylov.rest.BaseTest;
import com.mmihaylov.rest.RestServicesException;
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
        String news = newsService.getNews(344189);
        Assert.assertNotNull(news);
    }
}
