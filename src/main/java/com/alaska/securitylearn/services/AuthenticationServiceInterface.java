package com.alaska.securitylearn.services;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.alaska.securitylearn.exceptions.TokenExpiredException;
import com.alaska.securitylearn.exceptions.UnauthorizedRequestException;
import com.alaska.securitylearn.exceptions.UserAlreadyExistException;
import com.alaska.securitylearn.exceptions.ValidationErrorsException;
import com.alaska.securitylearn.model.TokenRequest;
import com.alaska.securitylearn.model.User;

public interface AuthenticationServiceInterface {
    public User registerUser(BindingResult validationResult, User user)
            throws ValidationErrorsException, UserAlreadyExistException;

    public User authenticateUser(BindingResult validationResult, User user) throws ValidationErrorsException;

    public Map<String, String> requestAccessToken(TokenRequest refreshToken)
            throws UnauthorizedRequestException, TokenExpiredException;
}
