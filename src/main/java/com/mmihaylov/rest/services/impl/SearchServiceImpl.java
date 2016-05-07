package com.mmihaylov.rest.services.impl;

import com.google.inject.Inject;
import com.mmihaylov.rest.resources.model.NewsEntity;
import com.mmihaylov.rest.services.SearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;


public class SearchServiceImpl implements SearchService {

    private static final Logger LOG = LogManager.getLogger(SearchServiceImpl.class);

    Client client;

    @Inject
    public SearchServiceImpl(Client client) {
        this.client = client;
    }

    public NewsEntity search(String text) {
        return null;
    }
}