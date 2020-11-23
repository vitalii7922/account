package com.project.response;

import lombok.EqualsAndHashCode;


/**
 * used for messages in JSON format
 */
@EqualsAndHashCode
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
