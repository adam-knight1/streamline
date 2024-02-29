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

    /**
     * Finds a user by their unique user ID.
     * This method queries the database using the user ID to retrieve the corresponding user record.
     *
     * @param userId The unique identifier of the user to find.
     * @return The UserRecord if the user is found, otherwise null.
     */
    public UserRecord findByUserId(String userId) {
        //I need to add more logging statements here
        return userDao.findUserById(userId);
    }
    /**
     * Finds a user by their username.
     * Utilizes the username index to search the database for the user record.
     *
     * @param username The username of the user to find.
     * @return The UserRecord if the user is found, otherwise null.
     */
    public UserRecord findByUserName(String username) {
        try {
            return userDao.findUserByUsername(username);
        } catch (Exception e) {
            System.out.println("Error from userDao showing up in lambdaUserService");
        }
        return null;
    }

    /**
     * Creates a new user in the database.
     * Validates the user record and uses UserDao to persist it.
     * Constructs a response object containing the user details.
     *
     * @param userRecord The record of the user to be created.
     * @return A response object containing the new user's details.
     * @throws IllegalArgumentException If the user record contains null values.
     */
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

