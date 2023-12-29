package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "task")
public class TaskRecord {
    private String userId;
    private String taskId;
    private String taskListName;
    private String taskName;
    private String description;
    private boolean completed;

    public TaskRecord(){
    }

    public TaskRecord(String userId, String taskId, String taskListName, String taskName, String description, boolean completed) {
        this.userId = userId;
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.taskName = taskName;
        this.description = description;
        this.completed = completed;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBRangeKey(attributeName = "taskId")
    public String getTaskId() {
        return taskId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @DynamoDBAttribute(attributeName = "taskListname")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskListName) {
        this.taskName = taskListName;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    @DynamoDBAttribute(attributeName = "taskListName")
    public String getTaskListName() {
        return taskName;
    }
    public void setTaskListName(String taskListName){
        this.taskName = taskListName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRecord that = (TaskRecord) o;
        return completed == that.completed && Objects.equals(userId, that.userId) &&
                Objects.equals(taskId, that.taskId) && Objects.equals(taskListName, that.taskListName) &&
                Objects.equals(taskName, that.taskName) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, taskId, taskListName, taskName, description, completed);
    }
}
