package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class TaskListCreateRequest {
    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("taskListName")
    private String taskListName;

//    @JsonProperty("existingTaskListName")
//    private String existingTaskListName;
//
//    @JsonProperty("newTaskListName")
//    private String newTaskListName;

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

    //    public String getExistingTaskListName() {
//        return existingTaskListName;
//    }
//
//    public void setExistingTaskListName(String existingTaskListName) {
//        this.existingTaskListName = existingTaskListName;
//    }
//
//    public String getNewTaskListName() {
//        return newTaskListName;
//    }
//
//    public void setNewTaskListName(String newTaskListName) {
//        this.newTaskListName = newTaskListName;
//    }
}
