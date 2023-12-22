package com.kenzie.capstone.service;
import com.kenzie.capstone.service.dao.TaskDao;

import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;

import java.util.List;
import java.util.UUID;

public class LambdaTaskService {

    private TaskDao taskDao;


//This is an example template i have not edited anything in here yet. -AM

    public LambdaTaskService(TaskDao taskDao){
        this.taskDao =taskDao;
    }

    public TaskRequest retrieveTaskRequest(String userId) {
        List<TaskRecord> taskRecords = taskDao.getTasksByUserId(userId);
        if (!taskRecords.isEmpty()) {
            TaskRecord record = taskRecords.get(0);
            return new TaskRequest(record.getUserId(), record.getTaskId());
        }
        return null;
    }

    public TaskRequest createTaskRequest(String taskId) {
        String generatedId = UUID.randomUUID().toString();
        TaskRecord createdTask = taskDao.createTaskRecord(generatedId, taskId);
        return new TaskRequest(generatedId, taskId);
    }

    public TaskRecord updateTask(String taskId, TaskRequest taskRequest) {
        TaskRecord existingTask = taskDao.getTaskRecordById(taskId);

        if (existingTask == null) {
            throw new IllegalArgumentException("Task with ID " + taskId + " does not exist");
        }

        // Update the properties of the existing task using data from taskRequest
        existingTask.setName(taskRequest.getName());
        existingTask.setDescription(taskRequest.getDescription());
        // Update other properties as needed

        return taskDao.updateTaskRecord(existingTask);
    }


}