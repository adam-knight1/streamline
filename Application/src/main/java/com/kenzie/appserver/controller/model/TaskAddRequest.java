package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class TaskAddRequest {
    @NotEmpty
    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;


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
}



