package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LambdaTaskService {
    private TaskDao taskDao;
    private static final Logger log = LoggerFactory.getLogger(LambdaTaskService.class);

    public LambdaTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    /**
     * Adds a task to the task list.
     * Validates the task record and persists it using the TaskDao.
     * Constructs a response object based on the result of the operation.
     *
     * @param taskRecord The task record to be added to the task list.
     * @return A response object containing details of the added task.
     * @throws IllegalArgumentException If the task record is null or contains null values.
     */
    public TaskAddResponse taskAddToTaskList(TaskRecord taskRecord) {
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
    /**
     * Retrieves tasks by user ID.
     * Calls the TaskDao to get a list of tasks associated with the provided user ID.
     *
     * @param userId The ID of the user whose tasks are being retrieved.
     * @return A list of TaskRecords associated with the user.
     * @throws IllegalArgumentException If the user ID is null or empty.
     */
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

    /**
     * Marks a task as complete.
     * Updates the status of a task to 'Complete' for the given user ID and task ID.
     *
     * @param userId The ID of the user who owns the task.
     * @param taskId The ID of the task to be marked as complete.
     * @return A response object containing the completion status of the task.
     */
    public CompleteTaskResponse completeTask(String userId, String taskId) {
        taskDao.completeTask(userId, taskId);
        CompleteTaskResponse response = new CompleteTaskResponse();
        response.setUserId(userId);
        response.setTaskId(taskId);
        response.setStatus("Complete");
        log.info("Task {} for user {} marked as complete.", taskId, userId);
        return response;
    }
}


