package com.alaska.securitylearn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Override
    public User registerUser(BindingResult validationResult, User user)
            throws ValidationErrorsException, UserAlreadyExistException {
        if (validationResult.hasErrors()) {
            throw new ValidationErrorsException(validationResult.getFieldErrors());
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
}
