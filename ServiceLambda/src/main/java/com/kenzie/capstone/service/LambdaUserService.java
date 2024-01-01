package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserResponse;

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

    public UserResponse createNewUser(UserRecord userRecord) {
        if (userRecord == null || userRecord.getEmail() == null || userRecord.getUsername() == null || userRecord.getPassword() == null) {
            log.error("The user recordRecord contains null values");
        }

        if (userRecord.getUserId() == null) {
            userRecord.setUserId(UUID.randomUUID().toString());
        }

        try {
            userDao.createUser(userRecord);
        } catch (Exception e) {
            log.error("Error creating user: ", e);

        }
        log.info("Successfully created user");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userRecord.getUserId());
        userResponse.setUsername(userRecord.getUsername());
        userResponse.setEmail(userRecord.getEmail());
        return userResponse;
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

