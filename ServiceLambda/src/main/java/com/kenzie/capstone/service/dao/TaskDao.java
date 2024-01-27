package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.TaskRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDao {
    DynamoDBMapper mapper;
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public TaskDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


    /*public TaskRecord addTask(TaskRecord taskRecord) {
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            Map<String, ExpectedAttributeValue> expectedAttributes = new HashMap<>();
            expectedAttributes.put("taskId", new ExpectedAttributeValue()
                    .withValue(new AttributeValue().withS(taskRecord.getTaskId()))
                    .withExists(false));

            saveExpression.setExpected(expectedAttributes);
            System.out.println("Saving taskRecord: " + taskRecord);

            mapper.save(taskRecord, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Task with taskId " + taskRecord.getTaskId() +
                    " already exists for userId " + taskRecord.getUserId());
        }
        return taskRecord;
    }
*/

    public TaskRecord addTask(TaskRecord taskRecord) {
        if (taskRecord == null || taskRecord.getUserId() == null || taskRecord.getTaskId() == null) {
            throw new IllegalArgumentException("Task record and its key attributes cannot be null");
        }

        try {
            System.out.println("Saving taskRecord: " + taskRecord);
            mapper.save(taskRecord);
        } catch (Exception e) {
            throw new RuntimeException("Error saving taskRecord: " + e.getMessage(), e);
        }
        return taskRecord;
    }


  /*  public TaskRecord addTask(TaskRecord taskRecord) {
        if (taskRecord == null) {
            logger.error("Task record is null");
            throw new IllegalArgumentException("Task record cannot be null");
        }
        if (taskRecord.getUserId() == null || taskRecord.getTaskId() == null ||
                taskRecord.getTitle() == null || taskRecord.getBody() == null) {
            logger.error("Task record contains null values");
            throw new IllegalArgumentException("Task record fields cannot be null");
        }

        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            Map<String, ExpectedAttributeValue> expectedAttributes = new HashMap<>();
            expectedAttributes.put("taskId", new ExpectedAttributeValue()
                    .withValue(new AttributeValue().withS(taskRecord.getTaskId()))
                    .withExists(false));

            saveExpression.setExpected(expectedAttributes);
            logger.info("Saving taskRecord: {}", taskRecord);

            mapper.save(taskRecord, saveExpression);
            return taskRecord;
        } catch (ConditionalCheckFailedException e) {
            logger.error("Task with taskId {} already exists for userId {}",
                    taskRecord.getTaskId(), taskRecord.getUserId(), e);
            throw new IllegalArgumentException("Task with taskId " + taskRecord.getTaskId() +
                    " already exists for userId " + taskRecord.getUserId());
        }*/




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


