package com.mmihaylov.rest.search;

/**
 * Created by mmihaylov on 5/7/16.
 */
public class IndexException extends RuntimeException {

    public IndexException(String message) {
        super(message);
    }

    public IndexException(Throwable throwable) {
        super(throwable);
    }

    public IndexException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
