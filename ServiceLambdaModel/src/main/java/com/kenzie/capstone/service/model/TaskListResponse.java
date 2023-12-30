package com.kenzie.capstone.service.model;

public class TaskListResponse {
    private String userId;
    private String taskListName;

    public TaskListResponse(String userId, String taskListName) {
        this.userId = userId;
        this.taskListName = taskListName;
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

    @Override
    public String toString() {
        return "TaskListResponse{" +
                "userId='" + userId + '\'' +
                ", taskListName='" + taskListName + '\'' +
                '}';
    }
}
