package com.mmihaylov.rest.services;

import com.mmihaylov.rest.RestServicesException;

public interface NewsService {

    /** Returns the news specified by id in plain text format. */
    String getNews(long id) throws RestServicesException;
}
