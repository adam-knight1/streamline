package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserResponseLambda;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LambdaUserService {
    private UserDao userDao;
    private String errorMessage;
    private static final Logger log = LoggerFactory.getLogger(LambdaUserService.class);

    public LambdaUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserRecord findByUserId(String userId) {
        //I need to add more logging statements here
        return userDao.findUserById(userId);
    }

    public UserRecord findByUserName(String username) {
        try {
            return userDao.findUserByUsername(username);
        } catch (Exception e) {
            System.out.println("Error from userDao showing up in lambdaUserService");
        }
        return null;
    }

    public UserResponseLambda createNewUser(UserRecord userRecord) {
        if (userRecord == null || userRecord.getEmail() == null || userRecord.getUsername() == null || userRecord.getPassword() == null) {
            log.error("The user record contains null values");
            throw new IllegalArgumentException("User record cannot contain null values");
        }

        log.info("userid is " + userRecord.getUserId());

        if (userRecord.getUserId() == null) {
            userRecord.setUserId(UUID.randomUUID().toString());
        }

        try {
            //this line calls createUser method in the userDao which then interacts with DynamoDB
            userDao.createUser(userRecord);
            log.info("Successfully created user");

            UserResponseLambda userResponseLambda = new UserResponseLambda();
            userResponseLambda.setUserId(userRecord.getUserId());
            userResponseLambda.setUsername(userRecord.getUsername());
            userResponseLambda.setEmail(userRecord.getEmail());
            return userResponseLambda;
        } catch (Exception e) {
            log.error("Error creating user: ", e);
            throw new RuntimeException("Error creating user", e);
        }
    }
}

