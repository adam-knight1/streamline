package com.kenzie.appserver.repositories.model;

import org.springframework.scheduling.config.Task;

import java.util.List;
import java.util.Objects;

public class TaskListRecord {
    public String userId;
    public String taskListName;
    public List<Task> tasks;


    public TaskListRecord(String userId, String taskListName, List<Task> tasks) {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskListRecord)) return false;
        TaskListRecord that = (TaskListRecord) o;
        return getUserId().equals(that.getUserId()) && getTaskListName().equals(that.getTaskListName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTaskListName());
    }
}
