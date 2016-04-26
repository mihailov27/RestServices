package com.mmihaylov.rest.html;

/**
 *
 * @param <R> - result of the parsing.
 */
public interface HtmlPageParser<R> {

    /** Parse the provided raw html page and returns a news entity */
    R parse(String htmlPage);
}
