package com.mmihaylov.rest.search.indexer;

import com.google.inject.Inject;
import com.mmihaylov.rest.search.model.NewsIndexEntity;
import org.elasticsearch.client.Client;

import java.util.HashMap;
import java.util.Map;

import static com.mmihaylov.rest.search.IndexNames.NEWS_INDEX;

public class NewsIndexer extends BaseIndexer<NewsIndexEntity> {

    @Inject
    public NewsIndexer(Client client) {
        super(client);
    }

    @Override
    protected Map<String, Object> getSource(NewsIndexEntity entity) {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("externalId", entity.getExternalId());
        source.put("title", entity.getTitle());
        source.put("content", entity.getContent());
        source.put("publicationDate", entity.getPublicationDate());
        source.put("createdDate", entity.getCreatedDate());
        return source;
    }

    @Override
    String getType() {
        return NEWS_INDEX;
    }

    @Override
    String getIndex() {
        return NEWS_INDEX;
    }
}
