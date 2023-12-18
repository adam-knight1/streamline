package com.kenzie.appserver.service.model;


import java.util.UUID;

public class Task  {
    private String taskId;
    private String taskName;
    private String taskDescription;
    private String taskStatus;

    public Task() {
        this.taskId = UUID.randomUUID().toString();
    }

    public Task(String taskName, String taskDescription, String taskStatus) {
        this();
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public void updateTaskStatus(String newStatus){
        this.taskStatus = newStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

}

