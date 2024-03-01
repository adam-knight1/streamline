package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.kenzie.capstone.service.model.TaskRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Data access object for handling Task operations with DynamoDB.
 */
public class TaskDao {
    DynamoDBMapper mapper;
    private static final Logger logger = LogManager.getLogger(UserDao.class);
    public TaskDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Adds a new TaskRecord to the database.
     * @param taskRecord The TaskRecord to be added.
     * @return The added TaskRecord.
     * @throws IllegalArgumentException if taskRecord or its key attributes are null.
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

    /**
     * Retrieves all tasks associated with a given userId.
     * @param userId The unique identifier of the user whose tasks are to be retrieved.
     * @return A list of TaskRecords for the specified userId.
     */
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

    /**
     * Marks a task as complete.
     * @param userId The unique identifier of the user.
     * @param taskId The unique identifier of the task to be marked as complete.
     * @throws IllegalArgumentException if the task is not found.
     */
    public void completeTask(String userId, String taskId) {
        TaskRecord taskRecord = mapper.load(TaskRecord.class, userId, taskId);
        if (taskRecord != null) {
            taskRecord.setStatus("Complete");
            mapper.save(taskRecord, new DynamoDBSaveExpression()
                    .withExpectedEntry("taskId",
                            new ExpectedAttributeValue(new AttributeValue().withS(taskId))));
            logger.info("Task with ID {} for user {} marked as complete.", taskId, userId);
        } else {
            logger.error("Task with ID {} for user {} not found.", taskId, userId);
            throw new IllegalArgumentException("Task not found.");
        }
    }
}


