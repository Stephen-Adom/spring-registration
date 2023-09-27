package com.alaska.securitylearn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.alaska.securitylearn.exceptions.UserAlreadyExistException;
import com.alaska.securitylearn.exceptions.ValidationErrorsException;
import com.alaska.securitylearn.model.User;
import com.alaska.securitylearn.repository.UserRepository;

@Service
public class AuthenticationService implements AuthenticationServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Override
    public User registerUser(BindingResult validationResult, User user)
            throws ValidationErrorsException, UserAlreadyExistException {
        if (validationResult.hasErrors()) {
            throw new ValidationErrorsException(validationResult.getFieldErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User with email already exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("User with username already exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User newUser = User.builder().firstname(user.getFirstname()).lastname(user.getLastname())
                .email(user.getLastname()).username(user.getUsername())
                .password(this.passwordEncoder.encode(user.getPassword())).build();

        return this.userRepository.save(newUser);
    }

    @Override
    public User authenticateUser(BindingResult validationResult, User user) throws ValidationErrorsException {
        if (validationResult.hasErrors()) {
            throw new ValidationErrorsException(validationResult.getFieldErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            this.authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            throw new ValidationErrorsException(e.getMessage() + ": Username and/or password is incorrect",
                    HttpStatus.UNAUTHORIZED);
        }

        return (User) this.userRepository.findByUsername(user.getUsername());
    }
}
