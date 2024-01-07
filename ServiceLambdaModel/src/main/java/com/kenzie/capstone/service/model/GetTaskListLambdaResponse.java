package com.kenzie.capstone.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetTaskListLambdaResponse {
    private String userId;
    private String taskListName;
    private List<String> tasks;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetTaskListLambdaResponse that = (GetTaskListLambdaResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(taskListName, that.taskListName) && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, taskListName, tasks);
    }
}

