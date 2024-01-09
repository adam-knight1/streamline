package com.kenzie.capstone.service;
import com.kenzie.capstone.service.dao.TaskDao;

import com.kenzie.capstone.service.model.TaskListRecord;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
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

    public TaskResponseLambda updateTask(String taskId, String taskName, String taskDescription, boolean completed){
        if (taskId == null || taskName == null || taskName.isEmpty()){
            throw new InvalidDataException("Invalid task details");
        }
        TaskRecord existingRecord = taskDao.getTaskRecordByName(taskName);
        if (existingRecord == null){
            throw new InvalidDataException("Task with Name " + taskName + " not found");
        }
        if (!existingRecord.getTaskId().equals(taskId)){
            throw new InvalidDataException("Task ID does not match Task Name");
        }
       // existingRecord.setTaskName(taskName);
        existingRecord.setTaskDescription(taskDescription);
        existingRecord.setCompleted(completed);
        taskDao.updateTaskRecord(existingRecord);
        return TaskConverter.fromRecordToResponse(existingRecord);
    }
    public TaskResponseLambda createTask(TaskRecord taskRecord) {
        if (taskRecord == null || taskRecord.getTaskName() == null){
            log.error("The task record contains null values");
            throw new IllegalArgumentException("The task record cannot contain null values");
        }
        log.info("Task name is: " + taskRecord.getTaskName());

        try {
            taskDao.createTask(taskRecord);
            log.info("Succesfully created a task.");
            return new TaskResponseLambda(taskRecord.getTaskName(), taskRecord.getTaskId(),
                    taskRecord.getTaskDescription(), taskRecord.getUserId(), taskRecord.isCompleted());
        }catch (Exception e){
            log.error("Error creating a task: ", e);
            throw new RuntimeException("Error creating a task", e);
        }

    }

}
