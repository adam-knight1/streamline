package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@DynamoDBTable(tableName = "Task")
public class TaskRecord {
    @Id
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "taskId")
    private int taskId;
    @DynamoDBAttribute(attributeName = "taskName")
    private String taskName;
    @DynamoDBAttribute(attributeName = "taskDescription")
    private String taskDescription;
    @DynamoDBAttribute(attributeName = "completed")
    private boolean completed;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRecord that = (TaskRecord) o;
        return taskId == that.taskId && completed == that.completed && Objects.equals(userId, that.userId) && Objects.equals(taskName, that.taskName) && Objects.equals(taskDescription, that.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, taskId, taskName, taskDescription, completed);
    }

    @Override
    public String toString() {
        return "TaskRecord{" +
                "userId='" + userId + '\'' +
                ", taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", completed=" + completed +
                '}';
    }
}
