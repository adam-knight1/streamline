package com.kenzie.capstone.service.model;

import java.util.Objects;

public class TaskResponse{


    private String userId;
    private String taskListName;
    private String taskId;
    private String name;
    private String description;

    public TaskResponse() {
    }

    public TaskResponse(String userId, String taskListName, String taskId, String name, String description) {
        this.userId = userId;
        this.taskListName = taskListName;
        this.taskId = taskId;
        this.name = name;
        this.description = description;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskResponse that = (TaskResponse) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(taskListName, that.taskListName) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, taskListName, taskId, name, description);
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "userId='" + userId + '\'' +
                ", taskListName='" + taskListName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}