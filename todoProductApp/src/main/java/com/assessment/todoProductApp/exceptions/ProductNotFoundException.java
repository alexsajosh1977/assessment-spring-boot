package com.assessment.todoProductApp.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public ProductNotFoundException(String message) {
        super(message);
    }
}