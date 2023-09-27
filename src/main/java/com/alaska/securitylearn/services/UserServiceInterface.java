package com.alaska.securitylearn.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.alaska.securitylearn.exceptions.UserNotFoundException;

public interface UserServiceInterface {
    public UserDetails fetchUserDetailsByUsername(String username) throws UserNotFoundException;
}
