package com.project.exception;

public class ExceptionAccountNotFound extends RuntimeException {
    public ExceptionAccountNotFound(String firstName) {
        super(String.format("Account with name %s not found", firstName));
    }
}
