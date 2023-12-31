package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.Task;

import java.util.List;

public class TaskListResponse {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("name")
    private String taskListName;

    private List<com.kenzie.appserver.service.model.Task> tasks;

    public TaskListResponse(){}

    public TaskListResponse(String userId, String taskListName, List<com.kenzie.appserver.service.model.Task> tasks){
        this.userId = userId;
        this.taskListName = taskListName;
        this.tasks = tasks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<com.kenzie.appserver.service.model.Task> tasks) {
        this.tasks = tasks;
    }

}
