package com.mmihaylov.rest.services.impl;

import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.html.HTMLParser;
import com.mmihaylov.rest.services.NewsService;
import com.mmihaylov.rest.utils.URLReader;

import javax.inject.Inject;

public class NewsServiceImpl implements NewsService {

    private static final String NOVINI_TEMPLATE = "http://www.novini.bg/news/%d.html";

    private URLReader urlReader;

    private HTMLParser htmlParser;

    @Inject
    public NewsServiceImpl(URLReader urlReader, HTMLParser htmlParser) {
        this.urlReader = urlReader;
        this.htmlParser = htmlParser;
    }

    public String getNews(int id) throws RestServicesException {
        String fullAddress = String.format(NOVINI_TEMPLATE, id);
        String htmlPage = urlReader.readURLAsString(fullAddress);
        String news = htmlParser.parseNews(htmlPage);
        return news;
    }
}
