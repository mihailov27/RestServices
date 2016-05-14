package com.mmihaylov.rest.search;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mmihaylov.rest.search.client.ClientProvider;
import com.mmihaylov.rest.search.indexer.Indexer;
import com.mmihaylov.rest.search.indexer.NewsIndexer;
import org.elasticsearch.client.Client;

public class SearchModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Client.class).toProvider(ClientProvider.class).in(Singleton.class);
        bind(IndexConfiguration.class).to(IndexConfigurationImpl.class).asEagerSingleton();
        bind(Indexer.class).annotatedWith(Names.named("newsIndexer")).to(NewsIndexer.class).in(Singleton.class);
    }
}
