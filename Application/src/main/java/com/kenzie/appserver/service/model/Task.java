package com.kenzie.appserver.service.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;
public class Task  {
    private String taskId;
    private String userId;
    private String taskDescription;
    private boolean taskStatus;


    public Task() {
        this.taskId = UUID.randomUUID().toString();
    }


    public Task(String taskName, String taskDescription, boolean taskStatus) {
        this();
        this.userId = userId;
        this.taskDescription = taskDescription;
        this.completed = completed;
    }

    public void updateTaskStatus(boolean newStatus){
        this.taskStatus = newStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;

    }
}
