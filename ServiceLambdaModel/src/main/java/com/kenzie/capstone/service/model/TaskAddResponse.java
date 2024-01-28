package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskAddResponse {
  /*  @JsonProperty("taskId")
    private String taskId;
*/
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("status")
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
