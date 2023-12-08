package com.kenzie.appserver.repositories.model;

public class TaskListRecord {
    public String userId;
    public String taskListName;
    private User user;

    public TaskList(String userId, String taskListName, User user) {
        this.userId = userId;
        this.taskListName = taskListName;
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }
}
