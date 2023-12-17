package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserRecord;

public class LambdaUserService {

    private UserDao userDao;

    public LambdaUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserRecord findByUserId(String userId) {
        return userDao.findUserById(userId);
    }

    public UserRecord createNewUser(UserRecord userRecord) {
        try {
            return userDao.createUser(userRecord);
        } catch (Exception e) {
            return null;
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

