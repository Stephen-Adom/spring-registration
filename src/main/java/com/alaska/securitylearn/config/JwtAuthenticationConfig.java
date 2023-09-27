package com.alaska.securitylearn.config;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alaska.securitylearn.exceptions.UserNotFoundException;
import com.alaska.securitylearn.services.JwtService;
import com.alaska.securitylearn.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class JwtAuthenticationConfig extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtHeader = request.getHeader("Authorization");
        String jwtUsername;

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtHeader.split(" ")[1];

        if (Objects.isNull(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtUsername = this.jwtService.extractUsernameFromToken(jwt);

        if (Objects.nonNull(jwtUsername) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            try {
                UserDetails userDetails = this.userService.fetchUserDetailsByUsername(jwtUsername);

                if (this.jwtService.isTokenValid(jwtUsername, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }

            } catch (UserNotFoundException e) {
                filterChain.doFilter(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }

}
