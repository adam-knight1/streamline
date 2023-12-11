package com.kenzie.appserver.repositories.model;

public class TaskListRecord {
    public String userId;
    public String taskListName;

    public TaskListRecord(String userId, String taskListName) {
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
}
