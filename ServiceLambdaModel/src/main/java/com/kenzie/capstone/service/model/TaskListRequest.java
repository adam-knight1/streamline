package com.kenzie.capstone.service.model;

public class TaskListRequest {
    private String userId;
    private String taskListName;

    public TaskListRequest(String userId, String taskListName) {
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

//    @Override
//    public String toString() {
//        return "TaskListRequest{" +
//                "userId='" + userId + '\'' +
//                ", taskListName='" + taskListName + '\'' +
//                '}';
//    }
}
