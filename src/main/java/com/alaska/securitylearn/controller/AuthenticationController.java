package com.alaska.securitylearn.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alaska.securitylearn.exceptions.UserAlreadyExistException;
import com.alaska.securitylearn.exceptions.ValidationErrorsException;
import com.alaska.securitylearn.model.AuthResponse;
import com.alaska.securitylearn.model.User;
import com.alaska.securitylearn.model.UserDto;
import com.alaska.securitylearn.model.validationGroups.LoginValidationGroup;
import com.alaska.securitylearn.model.validationGroups.RegisterValidationGroup;
import com.alaska.securitylearn.services.AuthenticationService;
import com.alaska.securitylearn.services.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(
            @Validated(RegisterValidationGroup.class) @RequestBody User user,
            BindingResult validationResult) throws ValidationErrorsException, UserAlreadyExistException {

        User newUser = this.authService.registerUser(validationResult, user);

        String token = this.generateJwt(newUser);
        String refreshToken = this.jwtService.generateRefreshToken(newUser);

        AuthResponse responseBody = AuthResponse.builder().status(HttpStatus.CREATED)
                .data(this.buildDto(newUser)).accessToken(token).refreshToken(refreshToken).build();

        return new ResponseEntity<AuthResponse>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Validated(LoginValidationGroup.class) @RequestBody User user,
            BindingResult validationResult) throws ValidationErrorsException {
        User authUser = this.authService.authenticateUser(validationResult, user);

        String token = this.generateJwt(authUser);
        String refreshToken = this.jwtService.generateRefreshToken(authUser);

        AuthResponse responseBody = AuthResponse.builder().status(HttpStatus.OK)
                .data(this.buildDto(authUser)).accessToken(token).refreshToken(refreshToken).build();

        return new ResponseEntity<AuthResponse>(responseBody, HttpStatus.OK);
    }

    private UserDto buildDto(User newUser) {
        return UserDto.builder().id(newUser.getId()).firstname(newUser.getFirstname()).lastname(newUser.getLastname())
                .email(newUser.getEmail()).username(newUser.getUsername()).createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt()).enabled(newUser.isEnabled()).build();
    }

    private String generateJwt(User user) {
        Map<String, Object> userClaims = new HashMap<String, Object>();
        userClaims.put("iss", "Alask Security");
        userClaims.put("roles", user.getAuthorities());

        return this.jwtService.generateToken(userClaims, user);
    }
}
