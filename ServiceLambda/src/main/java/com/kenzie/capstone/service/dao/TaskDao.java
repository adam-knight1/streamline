package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.TaskRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

}
