package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "Task")
public class TaskRecord {
    @Id
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;

    private String taskId;

    private String taskContent;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "taskId")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @DynamoDBAttribute(attributeName = "taskContent")
    public String getTaskContent() {
        return taskContent;
    }
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }
}
