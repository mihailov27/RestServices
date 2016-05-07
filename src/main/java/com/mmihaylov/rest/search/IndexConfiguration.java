package com.mmihaylov.rest.search;

public interface IndexConfiguration {

    /** Verify whether the news index exists and create it if it does not exist yet. */
    void init();

    /** Creates the index, if it does not exist. */
    void createIfNotExists(String indexName);
}
