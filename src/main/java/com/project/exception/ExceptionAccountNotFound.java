package com.project.exception;

/**
 * thrown if an account not found
 */
public class ExceptionAccountNotFound extends RuntimeException {
    public ExceptionAccountNotFound(String firstName) {
        super(String.format("Account with name %s not found", firstName));
    }
}
