package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.TaskListService;
import com.kenzie.capstone.service.model.*;

import java.util.List;

public class TaskListDao {

    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public TaskListDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public TaskListRequest storeExampleData(TaskListRequest taskListRequest) {
        try {
            mapper.save(taskListRequest, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("userId has already been used");
        }

        return taskListRequest;
    }

    public List<TaskListRecord> getTaskListData(String userId) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);

        DynamoDBQueryExpression<TaskListRecord> queryExpression = new DynamoDBQueryExpression<TaskListRecord>()
                .withHashKeyValues(taskListRecord)
                .withConsistentRead(false);

        return mapper.query(TaskListRecord.class, queryExpression);
    }

    public TaskListRecord setTaskListData(String userId, String taskListName) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(taskListName);

        try {
            mapper.save(taskListRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("userId already exists");
        }

        return taskListRecord;
    }

    public List<TaskListRecord> getTaskListsByUserId(String userId) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);

        DynamoDBQueryExpression<TaskListRecord> queryExpression = new DynamoDBQueryExpression<TaskListRecord>()
                .withHashKeyValues(taskListRecord)
                .withConsistentRead(false);

        return mapper.query(TaskListRecord.class, queryExpression);
    }

    public TaskListRecord getTaskListsByTaskListName(String userId, String taskListName) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(taskListName);

        DynamoDBQueryExpression<TaskListRecord> queryExpression = new DynamoDBQueryExpression<TaskListRecord>()
                .withHashKeyValues(taskListRecord)
                .withConsistentRead(false);

        return  mapper.query(TaskListRecord.class, queryExpression).get(0);
    }

    public TaskListResponse createTaskListRecord(String userId, String taskListName) {
        TaskListRequest taskListRequest = new TaskListRequest();
        taskListRequest.setUserId(userId);
        taskListRequest.setTaskListName(taskListName);

        try {
            mapper.save(taskListRequest, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("userId already exists");
        }

        return new TaskListResponse(userId, taskListName);
    }

    public TaskListResponse updateTaskListRecord(String userId, String taskListName) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(taskListName);

        try {
            mapper.save(taskListRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(true)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("userId and or taskListName does not exist");
        }

        return new TaskListResponse(userId, taskListName);
    }
}
