package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskAddResponseModel {
    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;


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
