package com.project.exception;

/**
 * thrown if an account with such a first name exists
 */
public class ExceptionAccountExists extends RuntimeException {
    public ExceptionAccountExists(String firstName) {
        super(String.format("Account with name %s already exists", firstName));
    }
}
