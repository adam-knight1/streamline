package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.UserRecord;

import java.util.List;

public class UserDao {
    private DynamoDBMapper mapper;

    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

        public UserRecord createUser(UserRecord userRecord) {
            try {
                mapper.save(userRecord, new DynamoDBSaveExpression()
                        .withExpected(ImmutableMap.of(
                                "userId",
                                new ExpectedAttributeValue().withExists(false)
                        )));
            } catch (ConditionalCheckFailedException e) {
                throw new IllegalArgumentException("User with ID " + userRecord.getUserId() + " already exists");
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
    }



