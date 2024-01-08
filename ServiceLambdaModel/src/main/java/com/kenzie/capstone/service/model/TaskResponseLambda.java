package com.kenzie.capstone.service.model;

import java.util.Objects;

public class TaskResponseLambda {


    private String userId;
    private String taskId;
    // This may be superfluous since the task is just a simple sentence "take the dog out" - OB
    private String taskName;
    private String taskDescription;
    private boolean completed;

    public TaskResponseLambda() {
    }

    public TaskResponseLambda(String userId, String taskId, String taskName, String taskDescription, boolean completed) {
        this.userId = userId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.completed = completed;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskResponseLambda that = (TaskResponseLambda) o;
        return completed == that.completed && Objects.equals(userId, that.userId) && Objects.equals(taskId, that.taskId) && Objects.equals(taskName, that.taskName) && Objects.equals(taskDescription, that.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, taskId, taskName, taskDescription, completed);
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", completed=" + completed +
                '}';
    }
}