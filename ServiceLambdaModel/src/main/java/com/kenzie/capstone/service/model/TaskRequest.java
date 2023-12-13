package com.kenzie.capstone.service.model;

import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;

import java.util.List;

public class TaskRequest {
    private String userId;
    private String taskListName;

    public TaskRequest(String userId, String taskListName) {
        this.userId = userId;
        this.taskListName = taskListName;
    }

    public TaskRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskListName;
    }

    public void setTaskId(String taskListName) {
        this.taskListName = taskListName;
    }

    @Override
    public String toString() {
        return "TaskRequest{" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskListName + '\'' +
                '}';
    }
}
