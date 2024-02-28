package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.model.TaskList;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.GetTaskListLambdaResponse;
import com.kenzie.capstone.service.model.TaskListRequest;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;
    private LambdaServiceClient lambdaServiceClient;
    private final CacheStore cacheStore;


    @Autowired
    public TaskListService(TaskListRepository taskListRepository, LambdaServiceClient lambdaServiceClient, CacheStore cacheStore) {
        this.taskListRepository = taskListRepository;
        this.lambdaServiceClient = lambdaServiceClient;
        this.cacheStore = cacheStore;
    }

//    public TaskList findTaskListByUserId(String userId){
//        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
//        if(taskListRecord.isPresent()){
//            TaskListRecord record = taskListRecord.get();
//            return transformToTaskList(record);
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list does not exist.");
//        }
//    }

    public TaskListResponse createTaskList(TaskListRequest request) throws JsonProcessingException {
        com.kenzie.capstone.service.model.TaskListResponse response = new com.kenzie.capstone.service.model.TaskListResponse();
        try {
            response = lambdaServiceClient.createTaskList(request);
        } catch (Exception e) {
            System.out.println(response);
            System.out.println("Task list creation unsuccessful.");
        }
        TaskList savedTaskList = new TaskList(request.getUserId(), request.getTaskListName());
        //cacheStore.add(savedTaskList.userId, savedTaskList);
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setUserId(request.getUserId());
        taskListResponse.setTaskListName(request.getTaskListName());
        taskListResponse.setTasks(Collections.emptyList());
        return taskListResponse;
    }

    public GetTaskListLambdaResponse findTaskListByUserId(String userId) throws JsonProcessingException {
        //this routes the call from taskListService to the LambdaService client, to interact with the findTaskListById lambda. -Adam
        return lambdaServiceClient.findTaskListByUserId(userId);
    }

}

