package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.model.*;
import converter.TaskConverter;
import exceptions.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class LambdaTaskService {

    private TaskDao taskDao;
    private static final Logger log = LoggerFactory.getLogger(LambdaTaskService.class);


    public LambdaTaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public TaskAddResponse taskAddToTaskList (TaskRecord taskRecord) {
        //this needs to be updated now after changing task record.
        if (taskRecord == null || taskRecord.getTitle() == null) {
            log.error("The task record contains null values");
            throw new IllegalArgumentException("task record cannot contain null values");
        }
        log.info("task title is " + taskRecord.getTitle());

        try {
            taskDao.addTask(taskRecord);
            log.info("Successfully added task");

            TaskAddResponse taskAddResponse = new TaskAddResponse();
            taskAddResponse.setTitle(taskRecord.getTitle());
            taskAddResponse.setBody(taskRecord.getBody());
            taskAddResponse.setUserId(taskRecord.getUserId());
            taskAddResponse.setStatus(taskRecord.getStatus());
            taskAddResponse.setTaskId(taskRecord.getTaskId());

            return taskAddResponse;
        } catch (Exception e) {
            log.error("Error creating user: ", e);
            throw new RuntimeException("Error creating task", e);
        }
    }

    public List<TaskRecord> getTasksByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            log.error("User ID is null or empty");
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        try {
            List<TaskRecord> tasks = taskDao.getTasksByUserId(userId);
            log.info("Successfully retrieved tasks for user ID: {}", userId);
            return tasks;
        } catch (Exception e) {
            log.error("Error retrieving tasks for user ID: {} - Error: ", userId, e);
            throw new RuntimeException("Error retrieving tasks for user ID", e);
        }
    }




}


