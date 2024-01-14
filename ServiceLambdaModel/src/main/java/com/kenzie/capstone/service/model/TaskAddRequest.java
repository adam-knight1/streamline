package com.kenzie.capstone.service.model;


import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TaskAddRequest {
    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("status")
    private String status;

    public TaskAddRequest() {
    }

    public TaskAddRequest(String title, String body){
        this.body = body;
        this.title = title;
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
