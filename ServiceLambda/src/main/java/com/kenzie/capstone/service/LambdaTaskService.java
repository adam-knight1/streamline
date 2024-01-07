package com.kenzie.capstone.service;
import com.kenzie.capstone.service.dao.TaskDao;

import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskResponseLambda;
import converter.TaskConverter;
import exceptions.InvalidDataException;

public class LambdaTaskService {

    private TaskDao taskDao;

    public LambdaTaskService(TaskDao taskDao){
        this.taskDao =taskDao;
    }

    public TaskResponseLambda updateTask(String taskId, String taskName, String taskDescription, boolean completed){
        if (taskId == null || taskName == null || taskName.isEmpty()){
            throw new InvalidDataException("Invalid task details");
        }
        TaskRecord existingRecord = taskDao.getTaskRecordByName(taskName);
        if (existingRecord == null){
            throw new InvalidDataException("Task with Name " + taskName + "not found");
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

}
