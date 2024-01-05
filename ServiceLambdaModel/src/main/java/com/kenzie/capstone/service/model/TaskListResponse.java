package com.kenzie.capstone.service.model;

import java.util.List;

public class TaskListResponse {
    private String userId;
    private String taskListName;
    private List<String> tasks;

    public TaskListResponse(){}

    public TaskListResponse(String userId, String taskListName, List<String> tasks) {
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

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TaskListResponse{" +
                "userId='" + userId + '\'' +
                ", taskListName='" + taskListName + '\'' +
                '}';
    }
}
