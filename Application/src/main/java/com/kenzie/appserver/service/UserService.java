package com.kenzie.appserver.service;
import com.kenzie.appserver.DuplicateUsernameException;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private LambdaServiceClient lambdaServiceClient = new LambdaServiceClient();
    private ConcurrentHashMap<String, UserRecord> localLoginMap = new ConcurrentHashMap<>(); //added this threadsafe map for the local login


    @Autowired
    public UserService(LambdaServiceClient lambdaServiceClient, UserRepository userRepository) {
        this.lambdaServiceClient = lambdaServiceClient;
        this.userRepository = userRepository;
    }


    public UserResponseLambda findUserByUserId(String userId) throws JsonProcessingException {
        return lambdaServiceClient.findUserByUserId(userId);
    }

    public UserResponseLambda findUserByUsername(String username) throws JsonProcessingException{
        return lambdaServiceClient.findUserByUsername(username);
    }


    public UserResponse createNewUser(UserRequest userRequest) throws Exception {
        String validateUsername = userRequest.getUsername();
        UserResponseLambda duplicateUserProfile = findUserByUsername(validateUsername);

        if (duplicateUserProfile != null) {
            String duplicateUsername = duplicateUserProfile.getUsername();
            if (duplicateUsername.equalsIgnoreCase(validateUsername))
                throw new DuplicateUsernameException("Username is not available: " + validateUsername);
        }

        try {
            lambdaServiceClient.createUser(userRequest);
        } catch (Exception e) {
            logger.error("Error during user creation");
            throw new RuntimeException(e);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userRequest.getUserId());
        userResponse.setEmail(userRequest.getEmail());
        userResponse.setUsername(userRequest.getUsername());

        return userResponse;
    }
}
