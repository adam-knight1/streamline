package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.TaskRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class TaskDao {
    DynamoDBMapper mapper;
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public TaskDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


    public TaskRecord addTask (TaskRecord taskRecord) {
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();

            Map<String, ExpectedAttributeValue> expectedAttributes = ImmutableMap.of(
                    "title", new ExpectedAttributeValue().withExists(false),
                    "body", new ExpectedAttributeValue().withExists(false)
            );

            saveExpression.setExpected(expectedAttributes);
            System.out.println("Saving taskRecord: " + taskRecord);

            mapper.save(taskRecord, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("task " + taskRecord.getTitle() + " and body " + taskRecord.getBody() + " already exists");
        }
        return taskRecord;
    }


    public List<TaskRecord> getTasksByUserId(String userId) {
        DynamoDBQueryExpression<TaskRecord> queryExpression = new DynamoDBQueryExpression<>();

        TaskRecord hashKeyValues = new TaskRecord();
        hashKeyValues.setUserId(userId);

        queryExpression.setHashKeyValues(hashKeyValues);

        List<TaskRecord> taskList;
        try {
            taskList = mapper.query(TaskRecord.class, queryExpression);
            logger.info("Tasks retrieved for userId: {}", userId);
        } catch (Exception e) {
            logger.error("Error retrieving tasks for userId: {}", userId, e);
            throw new RuntimeException("Error retrieving tasks", e);
        }

        return taskList;
    }
}


