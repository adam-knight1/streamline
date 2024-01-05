package com.kenzie.appserver.service;

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

@Service
public class UserService {
    private UserRepository userRepository;

    private LambdaServiceClient lambdaServiceClient = new LambdaServiceClient();

    @Autowired
    public UserService(LambdaServiceClient lambdaServiceClient, UserRepository userRepository) {
        this.lambdaServiceClient = lambdaServiceClient;
        this.userRepository = userRepository;
    }


    public Optional<UserRecord> authenticateUser(String username, String password){
        if (username == null || password == null) {
            System.out.println("Username or Password is null");
            return Optional.empty();
        }

        Optional<UserRecord> userRecord = userRepository.findByUsername(username);

        if (userRecord.isEmpty()) {
            System.out.println("User not found - authenticateUser");
            return Optional.empty();
        }
        if (userRecord.get().getPassword().equals(password)) {
            return userRecord;
        } else {
            System.out.println("Authentication failed");
            return Optional.empty();
        }
    }

    public String getUsernameByUserId(String userId) {
        Optional<UserRecord> userRecordOpt = userRepository.findByUserId(userId);
        if (userRecordOpt.isPresent()) {
            UserRecord userRecord = userRecordOpt.get();
            return userRecord.getUsername();
        }
            System.out.println("couldn't find userId");
        return null;
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
}
