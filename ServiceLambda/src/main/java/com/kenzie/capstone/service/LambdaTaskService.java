package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.model.*;
import converter.TaskConverter;
import exceptions.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LambdaTaskService {

    private TaskDao taskDao;
    private static final Logger log = LoggerFactory.getLogger(LambdaTaskService.class);


    public LambdaTaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public TaskAddResponse taskAddToTaskList (TaskRecord taskRecord) {
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
            taskAddResponse.setStatus(taskRecord.isCompleted());

            return taskAddResponse;
        } catch (Exception e) {
            log.error("Error creating user: ", e);
            throw new RuntimeException("Error creating task", e);
        }
    }

    }


