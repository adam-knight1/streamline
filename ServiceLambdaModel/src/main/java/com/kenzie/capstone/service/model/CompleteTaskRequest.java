package com.kenzie.capstone.service.model;
/**
 * CompleteTaskRequest is a DTO that carries the request data for completing a task.
 * It contains identifiers required to uniquely locate the task and mark it as complete
 * within the system.
 */
public class CompleteTaskRequest {
    private String userId;
    private String taskId;


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
}
