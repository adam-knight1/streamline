package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TaskListDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public TaskListDao(DynamoDBMapper mapper) {
        this.mapper = mapper; //inject Dynamo mapper
    }

    public TaskListRecord getTaskListByUserId(String userId) {
        //this works as is, no query needed as userId is the primary key
        return mapper.load(TaskListRecord.class, userId);
    }
    public TaskListRecord createTaskList(TaskListRecord taskListRecord) {
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();

            //UserId and TaskList name are the primary and range keys for TaskList table
            Map<String, ExpectedAttributeValue> expectedAttributes = ImmutableMap.of(
                    "userId", new ExpectedAttributeValue().withExists(false),
                    "taskListName", new ExpectedAttributeValue().withExists(false)
            );

            saveExpression.setExpected(expectedAttributes);

            System.out.println("Saving taskListRecord: " + taskListRecord); //for debugging
            mapper.save(taskListRecord, saveExpression);

        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Task list for user with ID " +
                    taskListRecord.getUserId() + " already exists.");
        }
        return taskListRecord;
    }

}
