package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ExampleRecord;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;

import java.util.List;

public class TaskDao {

    //this is not completed. I needed to use it in my taskservice -am
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     *
     * @param mapper Access to DynamoDB
     */
    public TaskDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public TaskRequest storeTaskData(TaskRequest taskRequest) {
        try {
            mapper.save(taskRequest, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return taskRequest;
    }

    public List<TaskRecord> getTaskData(String userId) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setUserId(userId);

        DynamoDBQueryExpression<TaskRecord> queryExpression = new DynamoDBQueryExpression<TaskRecord>()
                .withHashKeyValues(taskRecord)
                .withConsistentRead(false);

        return mapper.query(TaskRecord.class, queryExpression);
    }

    public TaskRecord setTaskData(String userId, String taskId) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setUserId(userId);
        taskRecord.setTaskId(taskId);

        try {
            mapper.save(taskRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return taskRecord;
    }

    public List<TaskRecord> getTasksByUserId(String userId) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setUserId(userId);

        DynamoDBQueryExpression<TaskRecord> queryExpression = new DynamoDBQueryExpression<TaskRecord>()
                .withHashKeyValues(taskRecord)
                .withConsistentRead(false);

        return mapper.query(TaskRecord.class, queryExpression);
    }

}
