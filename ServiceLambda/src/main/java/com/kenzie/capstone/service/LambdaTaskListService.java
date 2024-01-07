package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.TaskListDao;
import com.kenzie.capstone.service.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collections;

public class LambdaTaskListService {
    private TaskListDao taskListDao;
    private TaskDao taskDao;
    private static final Logger log = LoggerFactory.getLogger(LambdaTaskListService.class);

    @Inject
    public LambdaTaskListService(TaskListDao taskListDao, TaskDao taskDao) {
        this.taskListDao = taskListDao;
        this.taskDao = taskDao;
    }

    public TaskListRecord findTaskListByUserId(String userId) {
        //I need to add more logging statements here
        return taskListDao.findTaskListByUserById(userId);
    }

//    public TaskListRequest retrieveTaskListRequest(String userId) {
//        List<TaskListRecord> taskListRecords = taskListDao.getTaskListsByUserId(userId);
//        if (!taskListRecords.isEmpty()) {
//            TaskListRecord record = taskListRecords.get(0);
//            return new TaskListRequest(record.getUserId(), record.getTaskListName());
//        }
//        return null;
//    }

//    public TaskListRequest createTaskListRequest(String userId, String taskListName) {
//        TaskListRequest createdRecord = taskListDao.createTaskListRecord(userId, taskListName);
//        return new TaskListRequest(userId, taskListName);
//    }

    public TaskListResponse createTaskList(TaskListRecord taskListRecord){
        if (taskListRecord == null || taskListRecord.getUserId() == null || taskListRecord.getTaskListName() == null
        || taskListRecord.getTasks() == null) {
            log.error("The task list record contains null values");
            throw new IllegalArgumentException("The task list record cannot contain null values");
        }
        log.info("User id is " + taskListRecord.getUserId());

        try {
            taskListDao.createTaskList(taskListRecord);
            log.info("Successfully created task list.");
            return new TaskListResponse(taskListRecord.getUserId(),
                    taskListRecord.getTaskListName(), Collections.emptyList());
        } catch (Exception e) {
            log.error("Error creating task list: ", e);
            throw new RuntimeException("Error creating task list", e);
        }
//        if(taskListDao.getTaskListByUserId(taskListRequest.getUserId()) != null){
//            throw new IllegalArgumentException("TaskList with userId " + taskListRequest.getUserId() +
//                    " already exists.");
//        }
//        return taskListDao.createTaskListRecord(taskListRequest.getUserId(),
//                taskListRequest.getTaskListName(), Collections.emptyList());
    }

    public TaskListResponse updateTaskList(String userId, TaskListRequest taskListRequest) {
        String existingTaskListName = taskListRequest.getExistingTaskListName();
        String newTaskListName = taskListRequest.getNewTaskListName();
        TaskListRecord existingTaskList = taskListDao.getTaskListByTaskListName(userId, existingTaskListName);

        if (existingTaskList == null) {
            throw new IllegalArgumentException("TaskList with userId " + userId + " and TaskListName " + existingTaskListName
                    + " does not exist");
        }
//        log.info("User id is " + taskListRequest.getUserId());
        // Update the properties of the existing taskList using data from taskListRequest
        existingTaskList.setTaskListName(newTaskListName);

        TaskListResponse response = taskListDao.updateTaskListRecord(existingTaskList.getUserId(), existingTaskListName, newTaskListName);
        log.info("userId is: " + response.getUserId());
        log.info("taskListName is: " + response.getTaskListName());
        return taskListDao.updateTaskListRecord(existingTaskList.getUserId(), existingTaskListName, newTaskListName);
    }

    public TaskResponseLambda createTask(String userId, TaskRequest taskRequest) {
        if (taskRequest.getTaskName() == null || taskRequest.getTaskName().isEmpty()) {
            throw new IllegalArgumentException("Task name is required");
        }
        TaskListRecord taskListRecord = taskListDao.getTaskListByUserId(userId);

        if (taskListRecord == null) {
            throw new IllegalArgumentException("TaskList with userId " + userId + " does not exist");
        }

        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setUserId(userId);
        taskRecord.setTaskName(taskRequest.getTaskName());
        taskRecord.setTaskDescription(taskRequest.getTaskDescription());
        taskRecord.setTaskId(taskRequest.getTaskId());
        taskRecord.setCompleted(false);


        taskDao.storeTaskData(taskRecord);

        // Return the TaskResponse
        return new TaskResponseLambda(
                taskRecord.getUserId(), taskRecord.getTaskId(), taskRecord.getTaskName(),
                taskRecord.getTaskDescription(), taskRecord.isCompleted()
        );
    }
    }

