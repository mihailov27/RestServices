package com.mmihaylov.rest.services;

import com.mmihaylov.rest.resources.model.NewsEntity;

/** full text search service */
public interface SearchService {

    /** Full text search based on the provided text. */
    NewsEntity search(String text);
}
