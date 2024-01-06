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
        this.mapper = mapper;
    }

    public TaskListRecord getTaskListByUserId(String userId) {
        return mapper.load(TaskListRecord.class, userId);
//        TaskListRecord taskListRecord = new TaskListRecord();
//        taskListRecord.setUserId(userId);
//
//        DynamoDBQueryExpression<TaskListRecord> queryExpression = new DynamoDBQueryExpression<TaskListRecord>()
//                .withHashKeyValues(taskListRecord)
//                .withConsistentRead(false);
//
//        return mapper.query(TaskListRecord.class, queryExpression).get(0);
    }

    public TaskListRecord getTaskListByTaskListName(String userId, String taskListName) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(taskListName);

        DynamoDBQueryExpression<TaskListRecord> queryExpression = new DynamoDBQueryExpression<TaskListRecord>()
                .withHashKeyValues(taskListRecord)
                .withConsistentRead(false);

        return  mapper.query(TaskListRecord.class, queryExpression).get(0);
    }

    public TaskListRecord createTaskList(TaskListRecord taskListRecord) {
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();

            Map<String, ExpectedAttributeValue> expectedAttributes = ImmutableMap.of(
                    "userId", new ExpectedAttributeValue().withExists(false),
                    "taskListName", new ExpectedAttributeValue().withExists(false)
            );

            saveExpression.setExpected(expectedAttributes);
            System.out.println("Saving taskListRecord: " + taskListRecord);

            mapper.save(taskListRecord, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Task list for user with ID " +
                    taskListRecord.getUserId() + " already exists.");
        }
        return taskListRecord;
    }

    public TaskListResponse updateTaskListRecord(String userId, String existingTaskListName, String newTaskListName) {
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(newTaskListName);

        try {
            mapper.save(taskListRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId", new ExpectedAttributeValue().withExists(true),
                            "existingTaskListName", new ExpectedAttributeValue().withExists(true)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("userId and or taskListName does not exist");
        }

        return new TaskListResponse(userId, newTaskListName, Collections.emptyList());
    }

    //    public TaskListRequest storeExampleData(TaskListRequest taskListRequest) {
//        try {
//            mapper.save(taskListRequest, new DynamoDBSaveExpression()
//                    .withExpected(ImmutableMap.of(
//                            "userId",
//                            new ExpectedAttributeValue().withExists(false)
//                    )));
//        } catch (ConditionalCheckFailedException e) {
//            throw new IllegalArgumentException("userId has already been used");
//        }
//
//        return taskListRequest;
//    }
//
//    public List<TaskListRecord> getTaskListData(String userId) {
//        TaskListRecord taskListRecord = new TaskListRecord();
//        taskListRecord.setUserId(userId);
//
//        DynamoDBQueryExpression<TaskListRecord> queryExpression = new DynamoDBQueryExpression<TaskListRecord>()
//                .withHashKeyValues(taskListRecord)
//                .withConsistentRead(false);
//
//        return mapper.query(TaskListRecord.class, queryExpression);
//    }
//
//    public TaskListRecord setTaskListData(String userId, String taskListName) {
//        TaskListRecord taskListRecord = new TaskListRecord();
//        taskListRecord.setUserId(userId);
//        taskListRecord.setTaskListName(taskListName);
//
//        try {
//            mapper.save(taskListRecord, new DynamoDBSaveExpression()
//                    .withExpected(ImmutableMap.of(
//                            "userId",
//                            new ExpectedAttributeValue().withExists(false)
//                    )));
//        } catch (ConditionalCheckFailedException e) {
//            throw new IllegalArgumentException("userId already exists");
//        }
//
//        return taskListRecord;
//    }
}
