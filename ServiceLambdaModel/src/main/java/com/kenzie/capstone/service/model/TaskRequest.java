package com.kenzie.capstone.service.model;

public class TaskRequest {
    private String userId;
    private String taskId;
    // This may be superfluous since the task is just a simple sentence "take the dog out" - OB
    private String taskName;
    private String taskDescription;
    private boolean completed;

    public TaskRequest(String taskId, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
    }

    public TaskRequest() {

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
