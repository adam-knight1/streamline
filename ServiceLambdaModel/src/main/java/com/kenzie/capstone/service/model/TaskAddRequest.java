package com.kenzie.capstone.service.model;


import com.fasterxml.jackson.annotation.JsonProperty;



import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskAddRequest {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    public TaskAddRequest() {
    }

    public TaskAddRequest(String title, String body, String userId) {
        this.userId = userId; //temp debug to find if this is why it's not going into DB
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
