package com.mmihaylov.rest.utils;

/**
 *
 * @param <I> input
 * @param <R> result
 */
public interface Converter<I, R> {

    R convert(I input);

    I revert(R result);
}
