package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.model.TaskList;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.TaskListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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
        try {
            lambdaServiceClient.createTaskList(request);
        } catch (Exception e) {
            System.out.println("Task list creation unsuccessful.");
        }
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setUserId(request.getUserId());
        taskListResponse.setTaskListName(request.getTaskListName());
        taskListResponse.setTasks(Collections.emptyList());
        return taskListResponse;
//        TaskListResponse response = new TaskListResponse();
//        try {
//            com.kenzie.capstone.service.model.TaskListResponse taskListResponse =
//                    lambdaServiceClient.createTaskList(request);
//            response.setUserId(taskListResponse.getUserId());
//            response.setTaskListName(taskListResponse.getTaskListName());
//            response.setTasks(Collections.emptyList());
//        } catch (Exception e) {
//            System.out.println("Task list creation unsuccessful");
//        }
//        return response;
    }

    public TaskListRecord updateTaskListName(TaskListCreateRequest request, String userId) {
        Optional<TaskListRecord> optionalTaskListRecord = taskListRepository.findById(userId);

        if (optionalTaskListRecord.isPresent()) {
            TaskListRecord taskListRecord = optionalTaskListRecord.get();
            taskListRecord.setTaskListName(request.getTaskListName());
            return taskListRepository.save(taskListRecord);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list not found for the user.");
        }
    }

    /*
    public TaskListRecord updateTaskListName(TaskListCreateRequest request, String userId) {
        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
        if (taskListRecord.isPresent()) {
            TaskListRecord updatedRecord = taskListRepository.save(new TaskListRecord(userId, request.getTaskListName()));
            return updatedRecord;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list not found for the user.");
        }
    }

     */

//    public boolean deleteTaskListByUserId(String userId){
//        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
//        if(taskListRecord.isPresent()){
//            taskListRepository.deleteById(userId);
//            return true;
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list not found for the user.");
//        }
//    }

    private TaskList transformToTaskList(TaskListRecord record){

        return new TaskList(record.getUserId(), record.getTaskListName());
    }


}

