package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
@DynamoDBTable(tableName = "Task")
public class TaskRecord {
    private String taskId;
    private String userId;
    private String title;
    private String body;
    private String status;
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }
    @DynamoDBRangeKey(attributeName = "taskId")
    public String getTaskId() {
        return taskId;
    }


    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute(attributeName = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

