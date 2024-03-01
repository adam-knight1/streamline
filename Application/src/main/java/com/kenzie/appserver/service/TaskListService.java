package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.GetTaskListLambdaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListService {
    @Autowired
    private TaskListRepository taskListRepository;
    private LambdaServiceClient lambdaServiceClient;


    @Autowired
    public TaskListService(TaskListRepository taskListRepository, LambdaServiceClient lambdaServiceClient) {
        this.taskListRepository = taskListRepository;
        this.lambdaServiceClient = lambdaServiceClient;

    }

   /* public GetTaskListLambdaResponse findTaskListByUserId(String userId) throws JsonProcessingException {
        //this routes the call from taskListService to the LambdaService client, to interact with the findTaskListById lambda.
        return lambdaServiceClient.findTaskListByUserId(userId);
    }*/
}

