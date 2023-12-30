package com.kenzie.appserver.service.model;


import java.util.UUID;

public class Task {
    private String taskId;
    private String userId;
    private String taskDescription;
    private boolean completed;

    public Task() {
        this.taskId = UUID.randomUUID().toString();
    }

    public Task(String taskName, String taskDescription, boolean completed) {
        this();
        this.userId = userId;
        this.taskDescription = taskDescription;
        this.completed = completed;
    }

    public void updateTaskStatus(boolean newStatus) {
        this.completed = newStatus;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
