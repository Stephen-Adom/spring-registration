package com.alaska.securitylearn.services;

import org.springframework.validation.BindingResult;
import com.alaska.securitylearn.exceptions.UserAlreadyExistException;
import com.alaska.securitylearn.exceptions.ValidationErrorsException;
import com.alaska.securitylearn.model.User;

public interface AuthenticationServiceInterface {
    public User registerUser(BindingResult validationResult, User user)
            throws ValidationErrorsException, UserAlreadyExistException;
}
