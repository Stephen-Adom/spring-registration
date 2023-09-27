package com.alaska.securitylearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.alaska.securitylearn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public UserDetails findByUsername(String username);

    public Boolean existsByEmail(String email);

    public Boolean existsByUsername(String username);
}
