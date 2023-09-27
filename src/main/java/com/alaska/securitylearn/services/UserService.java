package com.alaska.securitylearn.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.alaska.securitylearn.exceptions.UserNotFoundException;
import com.alaska.securitylearn.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails fetchUserDetailsByUsername(String username) throws UserNotFoundException {
        UserDetails user = this.userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("Username not found", HttpStatus.NOT_FOUND);
        }
        return user;
    }
}
