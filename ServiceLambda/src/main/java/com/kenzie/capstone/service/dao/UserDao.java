package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.UserRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    private DynamoDBMapper mapper;

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


        /*public List<UserRecord> findUserByEmail(String email) {
            UserRecord userRecord = new UserRecord();
            userRecord.setEmail(email);

            DynamoDBQueryExpression<UserRecord> queryExpression = new DynamoDBQueryExpression<UserRecord>()
                    .withIndexName("email") //I need to set up the GSI for this to work
                    .withConsistentRead(false)
                    .withHashKeyValues(userRecord);

            return mapper.query(UserRecord.class, queryExpression);
        }
*/

    //idk if this will work as is with just the save and delete operations as is
        public UserRecord updateUser(UserRecord userRecord) {
            mapper.save(userRecord);
            return userRecord;
        }

        public void deleteUser(String userId) {
            UserRecord userRecord = mapper.load(UserRecord.class, userId);
            if (userRecord != null) {
                mapper.delete(userRecord);
            }
        }
    public List<UserRecord> findByUsername(String username) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUsername(username);

        DynamoDBQueryExpression<UserRecord> queryExpression = new DynamoDBQueryExpression<UserRecord>()
                .withIndexName("UsernameIndex") //gonna query the GSI!
                .withConsistentRead(false)
                .withHashKeyValues(userRecord);

        PaginatedQueryList<UserRecord> result = mapper.query(UserRecord.class, queryExpression);
        return new ArrayList<>(result);
    }

}