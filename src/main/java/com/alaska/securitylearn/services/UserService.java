package com.alaska.securitylearn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.alaska.securitylearn.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails fetchUserDetailsByUsername(String username) {
        UserDetails user = this.userRepository.findByUsername(username);
        return user;
    }
}
