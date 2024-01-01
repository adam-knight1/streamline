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
        return userDao.findUserById(userId);
    }

    public UserResponseLambda createNewUser(UserRecord userRecord) {
        if (userRecord == null || userRecord.getEmail() == null || userRecord.getUsername() == null || userRecord.getPassword() == null) {
            log.error("The user record contains null values");
            throw new IllegalArgumentException("User record cannot contain null values");
        }

        if (userRecord.getUserId() == null) {
            userRecord.setUserId(UUID.randomUUID().toString());
        }

        try {
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


    public UserRecord updateUser(UserRecord userRecord) {
        return userDao.updateUser(userRecord);
    }

    public boolean deleteUser(String userId) {
        try {
            userDao.deleteUser(userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

