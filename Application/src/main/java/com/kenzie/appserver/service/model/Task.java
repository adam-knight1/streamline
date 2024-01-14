package com.kenzie.appserver.service.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;
public class Task{
    private String title;
    private String body;
    private String status;

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