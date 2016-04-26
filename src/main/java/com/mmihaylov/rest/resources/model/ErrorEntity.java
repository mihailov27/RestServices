package com.mmihaylov.rest.resources.model;

public class ErrorEntity {

    private String title;

    private String message;

    public ErrorEntity() {
        this(null, null);
    }

    public ErrorEntity(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
