package com.kenzie.capstone.service.model;

public class TaskListRequest {
    private String userId;
    private String taskListName;
    private String newTaskListName;
    private String existingTaskListName;

    public TaskListRequest(String userId, String taskListName) {
        this.userId = userId;
        this.taskListName = taskListName;
    }

    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }

    //    public TaskListRequest(String userId, String newTaskListName) {
//        this.userId = userId;
//        this.newTaskListName = newTaskListName;
//        this.existingTaskListName = null;
//    }

    public TaskListRequest(String userId, String newTaskListName, String existingTaskListName) {
        this.userId = userId;
        this.newTaskListName = newTaskListName;
        this.existingTaskListName = existingTaskListName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewTaskListName() {
        return newTaskListName;
    }

    public void setNewTaskListName(String newTaskListName) {
        this.newTaskListName = newTaskListName;
    }

    public String getExistingTaskListName() {
        return existingTaskListName;
    }

    public void setExistingTaskListName(String existingTaskListName) {
        this.existingTaskListName = existingTaskListName;
    }

    //    @Override
//    public String toString() {
//        return "TaskListRequest{" +
//                "userId='" + userId + '\'' +
//                ", taskListName='" + taskListName + '\'' +
//                '}';
//    }
}
