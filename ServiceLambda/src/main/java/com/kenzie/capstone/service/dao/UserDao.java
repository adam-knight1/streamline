package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.UserRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    private DynamoDBMapper mapper;
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

        public UserRecord createUser(UserRecord userRecord) {
            try {
                DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();

                Map<String, ExpectedAttributeValue> expectedAttributes = ImmutableMap.of(
                        "userId", new ExpectedAttributeValue().withExists(false),
                        "email", new ExpectedAttributeValue().withExists(false)
                );

                saveExpression.setExpected(expectedAttributes);
                System.out.println("Saving userRecord: " + userRecord);

                mapper.save(userRecord, saveExpression);
            } catch (ConditionalCheckFailedException e) {
                throw new IllegalArgumentException("User with ID " + userRecord.getUserId() + " and email " + userRecord.getEmail() + " already exists");
            }
            return userRecord;
        }


    public UserRecord findUserById(String userId) {
        return mapper.load(UserRecord.class, userId);
    }


    //This method finds the user in the DB by using the username range key
    //Returns first entry of PQL of UserRecords
    //Logging statements added to track request time
    public UserRecord findUserByUsername(String username) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUsername(username);

        long startTime = System.currentTimeMillis();
        logger.info("Query start: " + startTime);

        DynamoDBQueryExpression<UserRecord> queryExpression = new DynamoDBQueryExpression<UserRecord>()
                .withIndexName("UsernameIndex")
                .withConsistentRead(false)
                .withHashKeyValues(userRecord);

        PaginatedQueryList<UserRecord> result = mapper.query(UserRecord.class, queryExpression);

        long endTime = System.currentTimeMillis();
        logger.info("Query end: " + endTime);
        logger.info("Query duration: " + (endTime - startTime) + " ms"); //use to determine any time out issues

        if (!result.isEmpty()) {
            return result.get(0);
        }
        System.out.println("no user was found with that username -userdao");
        return null;
    }

}
