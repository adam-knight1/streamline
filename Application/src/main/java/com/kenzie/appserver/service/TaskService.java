
package com.kenzie.appserver.service;

//import com.kenzie.appserver.controller.model.TaskResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.controller.model.TaskAddResponseModel;
//import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.capstone.service.client.LambdaServiceClient;
//import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
 public class TaskService {
    private TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private LambdaServiceClient lambdaServiceClient = new LambdaServiceClient();

    @Autowired
    public TaskService(LambdaServiceClient lambdaServiceClient, TaskRepository taskRepository) {
        this.lambdaServiceClient = lambdaServiceClient;
        this.taskRepository = taskRepository;
    }

    public TaskAddResponse addTask(TaskAddRequest taskAddRequest) throws JsonProcessingException {

        try {
            TaskAddResponse response = lambdaServiceClient.addTaskToTaskList(taskAddRequest);
            return response;
        } catch (Exception e) {
            System.out.println("failure in taskService");
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            logger.error("Error adding task", e);
            throw e;
        }


      /*  TaskAddResponse taskAddResponse = new TaskAddResponse();
        taskAddResponse.setBody(taskAddRequest.getBody());
        taskAddResponse.setTitle(taskAddRequest.getTitle());
        taskAddResponse.setUserId(taskAddRequest.getUserId());
        taskAddResponse.setStatus(taskAddRequest.getStatus());
        taskAddResponse.setTaskId(taskAddRequest.getTaskId());*/

    }

    public GetAllTasksResponse getTasksByUserId(String userId) {
        try {
            List<TaskRecord> taskRecords = lambdaServiceClient.getTasksByUserId(userId);
            List<TaskResponse> taskResponses = taskRecords.stream()
                    .map(this::convertToTaskResponse)
                    .collect(Collectors.toList());
            GetAllTasksResponse response = new GetAllTasksResponse();
            response.setTasks(taskResponses);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving tasks for user ID: " + userId, e);
        }
    }

    private TaskResponse convertToTaskResponse(TaskRecord taskRecord) {
        TaskResponse response = new TaskResponse();
        response.setTaskId(taskRecord.getTaskId());
        response.setUserId(taskRecord.getUserId());
        response.setTitle(taskRecord.getTitle());
        response.setBody(taskRecord.getBody());
        response.setStatus(taskRecord.getStatus());
        return response;
    }
}




