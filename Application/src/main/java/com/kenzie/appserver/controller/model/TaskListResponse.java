package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.repositories.model.TaskRecord;
import java.util.List;

public class TaskListResponse {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("taskListName")
    private String taskListName;

    @JsonProperty("tasks")
    private List<TaskRecord> tasks;

    public TaskListResponse(){}

    public TaskListResponse(String userId, String taskListName, List<TaskRecord> tasks){
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

    public List<TaskRecord> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskRecord> tasks) {
        this.tasks = tasks;
    }

}
