package com.kenzie.appserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        try {
            // 1. Authenticate with Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    )
            );

            // 2. Invoke custom logic, e.g., AWS Lambda function
            boolean lambdaAuthResult = awsLambdaService.invokeAuthenticationLambda(credentials);

            if (lambdaAuthResult) {
                // Authentication successful
                return ResponseEntity.ok("Login successful");
            } else {
                // Lambda authentication failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Additional Lambda check failed");
            }
        } catch (AuthenticationException e) {
            // Spring Security authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    static class LoginCredentials {
        private String username;
        private String password;

        // Getters and setters
    }
}
