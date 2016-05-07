package com.mmihaylov.rest.search;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;

public class IndexConfigurationImpl implements IndexConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(IndexConfigurationImpl.class);

    private Client client;

    @Inject
    public IndexConfigurationImpl(Client client) {
        this.client = client;
    }

    @Inject
    public void init() {
        LOGGER.info("Init indexes.");
        createIfNotExists(IndexNames.NEWS_INDEX);
    }

    public void createIfNotExists(String indexName) {
        LOGGER.debug("Check if index '%s' exists.", indexName);
        boolean isExists = isIndexExists(indexName);
        if(!isExists) {
            createIndex(indexName);
        }
    }

    private void createIndex(String indexName) {
        LOGGER.debug("Creating index named '%s'", indexName);
        AdminClient admin = client.admin();
        CreateIndexRequestBuilder createIndexRequestBuilder = admin.indices().prepareCreate(indexName);
        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.get();
        LOGGER.info("Created index 'news':" + createIndexResponse.getContext());
    }

    private boolean isIndexExists(String indexName) {
        AdminClient admin = client.admin();
        IndicesExistsRequestBuilder indicesExistsRequestBuilder = admin.indices().prepareExists(indexName);
        IndicesExistsResponse indicesExistsResponse = indicesExistsRequestBuilder.get();
        boolean isExists = indicesExistsResponse.isExists();
        LOGGER.debug("Index 'news' exists result: %s", isExists);
        return isExists;
    }
}
