package com.kenzie.capstone.service.model;

import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;

import java.util.List;

public class TaskRequest {
    private String userId;
    private String taskListName;
    private String name;
    private String description;

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

    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "TaskRequest{" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskListName + '\'' +
                '}';
    }

}
