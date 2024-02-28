package com.kenzie.capstone.service.model;

public class CompleteTaskResponse {
    private String userId;
    private String taskId;
    private String status;

    public CompleteTaskResponse() {
    }

    public CompleteTaskResponse(String userId, String taskId, String status) {
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CompleteTaskResponse{" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

