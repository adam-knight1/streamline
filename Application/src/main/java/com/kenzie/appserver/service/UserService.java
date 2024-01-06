package com.kenzie.appserver.service;
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

    public String getUsernameByUserId(String userId) {
        //this method will apparently not work given the configuration of the project.
        Optional<UserRecord> userRecordOpt = userRepository.findByUserId(userId);
        if (userRecordOpt.isPresent()) {
            UserRecord userRecord = userRecordOpt.get();
            return userRecord.getUsername();
        }
            System.out.println("couldn't find userId");
        return null;

    }

    public UserResponseLambda findUserByUserId(String userId) throws JsonProcessingException {
        return lambdaServiceClient.findUserByUserId(userId);
    }

    public UserResponseLambda findUserByUsername(String username) throws JsonProcessingException{
        return lambdaServiceClient.findUserByUsername(username);
    }


    public UserResponse createNewUser(UserRequest userRequest) throws JsonProcessingException {
        try {
            lambdaServiceClient.createUser(userRequest);
        } catch (Exception e) {
            System.out.println("unsuccessful user creation");
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userRequest.getUserId());
            if (userRequest.getUserId() == null){
                userResponse.setUserId(UUID.randomUUID().toString());
            } //just added this -adam 12/31
        userResponse.setEmail(userRequest.getEmail());
        userResponse.setUsername(userRequest.getUsername());
        return userResponse;
    }

    public User transformToUser(UserRecord userRecord) {
        User user = new User();
        user.setUserId(userRecord.getUserId());
        user.setEmail(userRecord.getEmail());
        user.setUsername(userRecord.getUsername());
        user.setPassword(userRecord.getPassword());
        return user;
    }
     /* public User findByUserId(String userId) {
        System.out.println("Searching for userId: " + userId);
        User user = userRepository
                .findById(userId)
                .map(u -> new User(u.getUserId(),u.getUsername(), u.getPassword(), u.getEmail()))
                .orElse(null);
        if (user == null) {
            System.out.println("User with userId: " + userId + " not found.");
        } else {
            System.out.println("User found: " + user);
        }
        return user;
    }*/

    /*public User createNewUser(User user) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(user.getUserId());
        userRecord.setEmail(user.getEmail());
        userRecord.setPassword(user.getPassword());
        userRecord.setUsername(user.getUsername());

        if (userRecord.getUserId() != null ||
                userRecord.getEmail() != null ||
                userRecord.getPassword() != null ||
                userRecord.getUsername() != null) {
            try {
                userRepository.save(userRecord);
                return user;
            } catch (Exception e) {
                System.out.println("unable to save user" + e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }*/

      /*public Optional<UserRecord> authenticateUser(String username, String password){
        if (username == null || password == null) {
            logger.error("Username or Password is null");
            return Optional.empty();
        }



        Optional<UserRecord> userRecord = userRepository.findByUsername(username);

        if (userRecord.isEmpty()) {
            logger.warn("User not found - authenticateUser: {}", username);
            return Optional.empty();
        }
        if (userRecord.get().getPassword().equals(password)) {
            logger.info("User authenticated successfully: {}", username);
            return userRecord;
        } else {
            logger.warn("Authentication failed for user: {}", username);
            return Optional.empty();
        }
    }*/

    /*public boolean authenticateUser(String username, String submittedPassword) {
        UserRecord user;
        if (user != null) {
            String storedHashedPassword = user.getPassword();
            return checkPassword(submittedPassword, storedHashedPassword);
        }
        return false;
    }*/

}
