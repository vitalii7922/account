package com.project.response;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
