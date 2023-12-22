package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.TaskRecord;

import java.util.List;
import java.util.NoSuchElementException;

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


    public TaskRecord storeTaskData(TaskRecord taskRequest) {
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

//    public TaskRecord createTaskRecord(String generatedId, String taskId) {
//    }
//
//    public TaskRecord getTaskRecordById(String taskId) {
//    }
//
//    public TaskService updateTaskRecord(TaskRecord existingTask) {
//    }
    public TaskRecord createTaskRecord(String userId, String taskId) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setTaskId(taskId);
        taskRecord.setUserId(userId);
        
        if (userId == null || userId.isEmpty()){
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (taskId == null || taskId.isEmpty()){
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }
        
        mapper.save(taskRecord);
        return taskRecord;
        
    }

    public TaskRecord getTaskRecordById(String taskId) {
        if (taskId == null || taskId.isEmpty()){
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }
        TaskRecord taskRecord = null;

        try {
            taskRecord = mapper.load(TaskRecord.class, taskId);
        }catch (AmazonDynamoDBException e){
            e.printStackTrace();
            throw new RuntimeException("Error while fetching TaskRecord by taskId: " + taskId,e);
        }
        return taskRecord;
    }

    public TaskRecord updateTaskRecord(TaskRecord existingTask) {
        if (existingTask == null || existingTask.getTaskId() == null || existingTask.getTaskId().isEmpty()){
            throw new IllegalArgumentException("Existing TaskRecord or its ID cannot be null or empty");
        }
        try {
            TaskRecord retrievedTask = mapper.load(TaskRecord.class,existingTask.getTaskId());

            if (retrievedTask != null){
                retrievedTask.setDescription(existingTask.getDescription());
                retrievedTask.setCompleted(existingTask.isCompleted());

                mapper.save(retrievedTask);
            }else {
                throw new NoSuchElementException("No TaskRecord found for TaskId: " + existingTask.getTaskId());
            }
        }catch (AmazonDynamoDBException e){
            e.printStackTrace();
            throw new RuntimeException("Error while updating TaskRecord: " + existingTask.getTaskId(), e);
        }
        return existingTask;
    }
}
