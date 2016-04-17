package com.mmihaylov.rest;

public class RestServicesException extends RuntimeException {

    public RestServicesException(String message) {
        super(message);
    }

    public RestServicesException(Throwable throwable) {
        super(throwable);
    }

    public RestServicesException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
