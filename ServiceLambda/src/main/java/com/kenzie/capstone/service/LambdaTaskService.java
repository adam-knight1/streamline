package com.kenzie.capstone.service;
import com.kenzie.capstone.service.dao.TaskDao;

import com.kenzie.capstone.service.model.TaskListRecord;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;
import converter.TaskConverter;
import exceptions.InvalidDataException;

import java.util.List;
import java.util.UUID;

public class LambdaTaskService {

    private TaskDao taskDao;

    public LambdaTaskService(TaskDao taskDao){
        this.taskDao =taskDao;
    }

    public TaskResponse updateTask(int taskId,  String taskName, String taskDescription){
        if (taskId <= 0 || taskName == null || taskName.isEmpty()){
            throw new InvalidDataException("Invalid task details");
        }
        TaskRecord existingRecord = taskDao.getTaskRecordById(taskId);
        if (existingRecord == null){
            throw new InvalidDataException("Task with Id " + taskId + "not found");
        }
        existingRecord.setTaskName(taskName);
        existingRecord.setTaskDescription(taskDescription);
        taskDao.updateTaskRecord(existingRecord);
        return TaskConverter.fromRecordToResponse(existingRecord);
    }

}
