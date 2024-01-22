package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
@DynamoDBTable(tableName = "Task")
public class TaskRecord {
    private String title;
    private String taskListId;
    private String body;
    private boolean isCompleted;


    @DynamoDBHashKey(attributeName = "taskListId")
    public String getTaskListId() {
        return taskListId;
    }
    @DynamoDBRangeKey(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setTaskListId(String taskListId) {
        this.taskListId = taskListId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

