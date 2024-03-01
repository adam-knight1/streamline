package com.kenzie.capstone.service.model;

/**
 * TaskResponse is a DTO that represents the task data sent back to the client.
 * It contains information about a specific task, including its unique identifier, associated user ID,
 * title, body content, and status. This class is used to transport task details from the server to the client,
 * primarily when loading tasks on initial login or live reload
 */
public class TaskResponse {
    private String taskId;
    private String userId;
    private String title;
    private String body;
    private String status;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

