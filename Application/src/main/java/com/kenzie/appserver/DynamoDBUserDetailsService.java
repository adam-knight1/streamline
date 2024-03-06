
package com.kenzie.appserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * DynamoDBUserDetailsService is a service class implementing Spring Security's UserDetailsService,
 * which is a core interface in Spring Security framework. This service is responsible for retrieving
 * user-specific data. It is used by the AuthenticationManager to load details about the user during
 * authentication.
 *
 * Specifically, this class is designed to interact with Amazon DynamoDB to retrieve user information
 * stored in the database. It generally uses UserRepository to perform operations like finding a user by their
 * username or user ID.  To experiment with AWS Lambda, this class employs the FindUserByUsername lambda.
 * If the user is found, this class constructs and returns a CustomUserDetails object
 * populated with the user's information fetched from DynamoDB.
 *
 * This class also handles the case where a user is not found in the database by throwing
 * a UsernameNotFoundException, which Spring Security uses to handle authentication failures.
 */

@Service
public class DynamoDBUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(DynamoDBUserDetailsService.class);

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Optional<UserRecord> optionalUserRecord = userRepository.findByUserId(userId);

        if (optionalUserRecord.isPresent()) {
            UserRecord userRecord = optionalUserRecord.get();
            return new CustomUserDetails(userRecord);
        } else {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         UserRecord userRecord = new UserRecord();

        try {
            UserResponseLambda user = userService.findUserByUserId(username);
            userRecord.setUsername(user.getUsername());
            userRecord.setEmail(user.getEmail());
            userRecord.setUserId(username);
            userRecord.setPassword(user.getPassword());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
            return new CustomUserDetails(userRecord);
       /* } else {
            throw new UsernameNotFoundException("User not found with userId: " + username);
        }*/
    }
}


