package com.alaska.securitylearn.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedRequestException extends Exception {

    private HttpStatus status;

    public UnauthorizedRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
