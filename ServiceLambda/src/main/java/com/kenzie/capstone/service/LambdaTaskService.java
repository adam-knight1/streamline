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

    public TaskResponse updateTask(String taskId, TaskRequest task){
        if (task == null || taskId == null || task.getTaskId().length() ==0){
            throw new InvalidDataException("Request must contain a valid task ID");
        }
        TaskRecord record = TaskConverter.fromRequestToRecord(task);
        taskDao.updateTaskRecord(record);
        return TaskConverter.fromRecordToResponse(record);
    }

}
