package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.Task;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "TaskList")
public class TaskListRecord {
    @Id
    @DynamoDBHashKey(attributeName = "userId")
    public String userId;

    @DynamoDBAttribute(attributeName = "taskListName")
    public String taskListName;

    @DynamoDBAttribute(attributeName = "tasks")
    public List<Task> tasks;

    public TaskListRecord(String userId, String taskListName) {
        this.userId = userId;
        this.taskListName = taskListName;
        this.tasks = new ArrayList<>();
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

    public void addTask(Task task) {
        if(tasks == null){
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
