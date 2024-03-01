package com.kenzie.capstone.service.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TaskAddRequest is a DTO designed to encapsulate the data necessary to add a new task.
 * It includes the user ID to associate the task with, along with the title and body of the task.
 * This class is used to transport task creation data from the client to the server.
 */
public class TaskAddRequest {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    public TaskAddRequest() {
    }

    /**
     * Constructs a new TaskAddRequest with specified title, body, and user ID.
     * @param title The title of the task.
     * @param body The body or description of the task.
     * @param userId The ID of the user to whom the task belongs.
     */
    public TaskAddRequest(String title, String body, String userId) {
        this.userId = userId;
        this.title = title;
        this.body = body;
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

}
