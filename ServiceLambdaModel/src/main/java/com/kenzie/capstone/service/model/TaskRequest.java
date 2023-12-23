package com.kenzie.capstone.service.model;

import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;

import java.util.List;

public class TaskRequest {
    private String userId;
    private String taskName;
   // private String name;
    private String description;
    private boolean completed;

    public TaskRequest(String userId, String taskName) {
        this.userId = userId;
        this.taskName = taskName;
    }

    public TaskRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "TaskRequest{" +
                "userId='" + userId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
