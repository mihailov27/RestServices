package com.mmihaylov.rest.search.indexer;

import com.mmihaylov.rest.search.IndexException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import java.util.List;
import java.util.Map;

public abstract  class BaseIndexer<T> implements Indexer<T> {

    protected static final Logger LOG = LogManager.getLogger(BaseIndexer.class);

    private Client client;

    public BaseIndexer(Client client) {
        this.client = client;
    }

    public final void index(T entity) {
        String index = getIndex();
        String type = getType();
        Map<String, ?> source = getSource(entity);
        IndexResponse response = client.prepareIndex(index, type).setSource(source).get();
        LOG.debug("Index entity response: %s", response);
        if(!response.isCreated()) {
            throw new IndexException("Fail to index entity.");
        }
    }

    public final void indexBulk(List<T> entities) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for(T entity : entities) {
            IndexRequestBuilder indexRequestBuilder = client.prepareIndex(getIndex(), getType());
            indexRequestBuilder.setSource(getSource(entity));
            bulkRequestBuilder.add(indexRequestBuilder);
        }
        BulkResponse bulkResponse = bulkRequestBuilder.get();
        if(bulkResponse.hasFailures()) {
            LOG.error("Bulk request failed, message is '%s'", bulkResponse.buildFailureMessage());
            throw new IndexException("Bulk request failed: " + bulkResponse.buildFailureMessage());
        } else {
            LOG.debug("Bulk response is ok.");
        }
    }

    abstract protected Map<String, Object> getSource(T entity);

    abstract String getIndex();

    abstract String getType();
}