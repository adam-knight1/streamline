package com.kenzie.appserver.repositories.model;

public class TaskListRecord {
    public String userId;
    public String taskListName;

    public TaskList(String userId, String taskListName) {
        this.userId = userId;
        this.taskListName = taskListName;
    }

    public String getUserId() {
        return userId;
    }
}
