package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserResponse;

import java.util.UUID;

public class LambdaUserService {

    private UserDao userDao;

    public LambdaUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserRecord findByUserId(String userId) {
        return userDao.findUserById(userId);
    }

    public UserResponse createNewUser(UserRecord userRecord) {
        if (userRecord.getUserId() == null) {
            userRecord.setUserId(UUID.randomUUID().toString());
        }

            try {
            userDao.createUser(userRecord);
        } catch (Exception e) {
            return new UserResponse(); //will add something more descriptive -adam
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userRecord.getUserId());
        userResponse.setEmail(userRecord.getEmail());
        userResponse.setUserId(userRecord.getUserId());

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

