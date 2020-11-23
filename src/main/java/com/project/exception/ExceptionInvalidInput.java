package com.project.exception;

/**
 * thrown if incorrect input such as: first name null, last name null, invalid format of JSON
 */
public class ExceptionInvalidInput extends RuntimeException {
    public ExceptionInvalidInput(String message) {
        super(message);
    }
}
