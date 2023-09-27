package com.alaska.securitylearn.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alaska.securitylearn.model.ErrorResponse;
import com.alaska.securitylearn.model.ValidationErrorMessage;

@ControllerAdvice
@ResponseStatus
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getStatus(), exception.getMessage()),
                exception.getStatus());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedRequest(UserNotFoundException exception) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getStatus(), exception.getMessage()),
                exception.getStatus());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExistException exception) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getStatus(), exception.getMessage()),
                exception.getStatus());
    }

    @ExceptionHandler(ValidationErrorsException.class)
    public ResponseEntity<ValidationErrorMessage> handleValidationError(ValidationErrorsException exception) {
        List<String> messages = new ArrayList<String>();

        if (exception.getMessage() != null) {
            messages.add(exception.getMessage());
        } else {
            for (FieldError error : exception.getFieldErrors()) {
                messages.add(error.getDefaultMessage());
            }
        }

        ValidationErrorMessage errormessage = ValidationErrorMessage.builder().status(exception.getStatus())
                .messages(messages).build();

        return new ResponseEntity<ValidationErrorMessage>(errormessage, exception.getStatus());

    }
}
