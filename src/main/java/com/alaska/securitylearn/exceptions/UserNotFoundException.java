package com.alaska.securitylearn.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends Exception {

    private HttpStatus status;

    public UserNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}