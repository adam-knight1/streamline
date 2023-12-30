package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.TaskListDao;
import com.kenzie.capstone.service.model.*;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class LambdaTaskListService {
    // Example template not edited yet
    private TaskListDao taskListDao;
    private TaskDao taskDao;

    @Inject
    public LambdaTaskListService(TaskListDao taskListDao, TaskDao taskDao) {
        this.taskListDao = taskListDao;
        this.taskDao = taskDao;
    }

    public TaskListRequest retrieveTaskListRequest(String userId) {
        List<TaskListRecord> taskListRecords = taskListDao.getTaskListsByUserId(userId);
        if (!taskListRecords.isEmpty()) {
            TaskListRecord record = taskListRecords.get(0);
            return new TaskListRequest(record.getUserId(), record.getTaskListName());
        }
        return null;
    }

//    public TaskListRequest createTaskListRequest(String userId, String taskListName) {
//        TaskListRequest createdRecord = taskListDao.createTaskListRecord(userId, taskListName);
//        return new TaskListRequest(userId, taskListName);
//    }

    public TaskListResponse createTaskList(TaskListRequest taskListRequest){
        if(taskListDao.getTaskListsByUserId(taskListRequest.getUserId()) != null){
            throw new IllegalArgumentException("TaskList with userId " + taskListRequest.getUserId() +
                    " already exists.");
        }
        return taskListDao.createTaskListRecord(taskListRequest.getUserId(), taskListRequest.getTaskListName());
    }

    public TaskListResponse updateTaskList(String userId, TaskListRequest taskListRequest) {
        String taskListName = taskListRequest.getTaskListName();
        TaskListRecord existingTaskList = taskListDao.getTaskListsByTaskListName(userId, taskListName);

        if (existingTaskList == null) {
            throw new IllegalArgumentException("TaskList with userId " + userId + " and TaskListName " + taskListName
                    + " does not exist");
        }

        // Update the properties of the existing taskList using data from taskListRequest
        existingTaskList.setUserId(taskListRequest.getUserId());
        existingTaskList.setTaskListName(taskListRequest.getTaskListName());

        return taskListDao.updateTaskListRecord(existingTaskList.getUserId(), existingTaskList.getTaskListName());
    }

    public TaskResponse createTask(String userId, TaskRequest taskRequest) {
        if (taskRequest.getTaskName() == null || taskRequest.getTaskName().isEmpty()) {
            throw new IllegalArgumentException("Task name is required");
        }
        TaskListRecord taskListRecord = (TaskListRecord) taskListDao.getTaskListsByUserId(userId);

        if (taskListRecord == null) {
            throw new IllegalArgumentException("TaskList with userId " + userId + " does not exist");
        }

        String taskId = UUID.randomUUID().toString();
        TaskRecord taskRecord = new TaskRecord();

        taskDao.storeTaskData(taskRecord);

        // Return the TaskResponse
        return new TaskResponse(
                taskRecord.getUserId(), taskRecord.getTaskId(), taskRecord.getTaskName(),
                taskRecord.getTaskDescription(), taskRecord.isTaskStatus()
        );
    }
    }

