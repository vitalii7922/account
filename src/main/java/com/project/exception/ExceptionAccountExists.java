package com.project.exception;

public class ExceptionAccountExists extends RuntimeException {
    public ExceptionAccountExists(String firstName) {
        super(String.format("Account with name %s already exists", firstName));
    }
}
