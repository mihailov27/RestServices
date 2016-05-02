package com.mmihaylov.rest.services.impl;

import com.google.inject.Inject;
import com.mmihaylov.rest.FatalException;
import com.mmihaylov.rest.resources.model.NewsEntity;
import com.mmihaylov.rest.services.SearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SearchServiceImpl implements SearchService {

    private static final Logger LOG = LogManager.getLogger(SearchServiceImpl.class);

    // TODO - place in a resource file as singleton.
    Client client;

    @Inject
    public void setup() {
        try {
            LOG.debug("Init elastic client.");
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            AdminClient admin = client.admin();
            admin.indices().exists(new IndicesExistsRequest("newsIndex"), newsIndexExistsResponseListener);
        } catch (UnknownHostException uhe) {
            LOG.error("Fail to setup elasticsearch client.");
            throw new FatalException(uhe);
        }
    }

    public NewsEntity search(String text) {
        return null;
    }

    private void createIndex(String indexName) {
        LOG.debug("Creating index named '%s'", indexName);
        AdminClient admin = client.admin();
        CreateIndexRequestBuilder createIndexRequestBuilder = admin.indices().prepareCreate(indexName);
        //createIndexRequestBuilder.add
    }

    ActionListener<IndicesExistsResponse> newsIndexExistsResponseListener = new ActionListener<IndicesExistsResponse>() {

        public void onResponse(IndicesExistsResponse indicesExistsResponse) {
            boolean isExists = indicesExistsResponse.isExists();
            LOG.debug("Index 'news' exists result: %s", isExists);
            if(!isExists) {
                LOG.debug("Creation of index 'news' begins.");
                createIndex("newsIndex");
            }
        }

        public void onFailure(Throwable e) {
            LOG.error("Fail to verify whether the index exists.", e);
        }
    };
}