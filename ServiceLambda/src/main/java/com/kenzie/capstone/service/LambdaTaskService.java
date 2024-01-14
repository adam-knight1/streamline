package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.model.TaskAddResponse;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskResponseLambda;
import converter.TaskConverter;
import exceptions.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaTaskService {

    private TaskDao taskDao;
    private static final Logger log = LoggerFactory.getLogger(LambdaTaskService.class);


    public LambdaTaskService(TaskDao taskDao){
        this.taskDao =taskDao;
    }


    public TaskResponseLambda addTask(TaskRecord taskRecord) {
        if (taskRecord == null || taskRecord.getTitle() == null){
            log.error("The task record contains null values");
            throw new IllegalArgumentException("The task record cannot contain null values");
        }
        log.info("Task name is: " + taskRecord.getTitle());

        try {

            log.info("Successfully created a task.");
            return new TaskResponseLambda(taskRecord.getTaskName(),
                    taskRecord.getTaskDescription(), taskRecord.getUserId(), taskRecord.isCompleted());
        }catch (Exception e){
            log.error("Error creating a task: ", e);
            throw new RuntimeException("Error creating a task", e);
        }

    }

    public TaskAddResponse addTasktoTaskList(TaskRecord taskRecord){
        if (taskRecord == null || taskRecord.getTitle() == null){
            log.error("The task record contains null values");
            throw new IllegalArgumentException("The task record cannot contain null values");
        }
        log.info("Task name is: " + taskRecord.getTitle());

        try {

            log.info("Successfully created a task.");

            return newaskResponseLambda(taskRecord.getTaskName(),
                    taskRecord.getTaskDescription(), taskRecord.getUserId(), taskRecord.isCompleted());
        }catch (Exception e){
            log.error("Error creating a task: ", e);
            throw new RuntimeException("Error creating a task", e);
        }
    }

}
