package com.mmihaylov.rest.search.indexer;

import java.util.List;

public interface Indexer<T> {

    void index(T entity);

    void indexBulk(List<T> entities);
}
