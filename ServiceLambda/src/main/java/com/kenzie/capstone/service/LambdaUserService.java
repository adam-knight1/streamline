package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;

public class LambdaUserService {

    private UserDao userDao;
    public LambdaUserService(UserDao userDao) {
        this.userDao = userDao;
    }

}
