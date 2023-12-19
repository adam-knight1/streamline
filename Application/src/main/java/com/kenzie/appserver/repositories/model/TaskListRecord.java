package com.kenzie.appserver.repositories.model;

import com.kenzie.appserver.service.model.Task;

import java.util.List;
import java.util.Objects;

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
