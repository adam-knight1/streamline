package com.kenzie.appserver.service.model;


import java.util.ArrayList;
import java.util.List;

public class TaskList {
//    private String taskListId;
    public String userId;
    public String taskListName;
    public List<Task> tasks;

    public TaskList(String userId, String taskListName){
        this.userId = userId;
        this.taskListName = taskListName;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        if(task != null){
            this.tasks.add(task);
        }
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
