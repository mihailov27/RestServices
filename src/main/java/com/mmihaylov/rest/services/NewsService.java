package com.mmihaylov.rest.services;

import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.resources.model.NewsEntity;

public interface NewsService {

    /** Returns the news specified by id in plain text format. */
    NewsEntity getNews(long id) throws RestServicesException;
}
