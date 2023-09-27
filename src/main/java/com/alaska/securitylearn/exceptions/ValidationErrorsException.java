package com.alaska.securitylearn.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationErrorsException extends Exception {

    private List<FieldError> errors = new ArrayList<>();

    public ValidationErrorsException(List<FieldError> fieldErrors) {
        this.errors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return this.errors;
    }

}
