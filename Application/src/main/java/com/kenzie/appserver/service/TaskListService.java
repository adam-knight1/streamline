/*
package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.model.Task;
import com.kenzie.appserver.service.model.TaskList;
import com.kenzie.capstone.service.client.LambdaServiceClient;
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

    @Autowired
    public TaskListService(TaskListRepository taskListRepository, LambdaServiceClient lambdaServiceClient) {
        this.taskListRepository = taskListRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public TaskList findTaskListByUserId(String userId){
        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
        if(taskListRecord.isPresent()){
            TaskListRecord record = taskListRecord.get();
            return transformToTaskList(record);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list does not exist.");
        }
    }

    public TaskList createTaskList(TaskListCreateRequest request, String userId, String taskListName) {
        String requestedUserId = request.getUserId();
        //Check to ensure user id is correct
        if(!requestedUserId.equals(userId)){
            throw new IllegalArgumentException("Provided user id does not match the user id in the request.");
        }
        //Check to ensure TaskList with same user id doesn't already exist
        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
        if(taskListRecord.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Task list already exists for the user.");
        }
        //Create the task list
        TaskList newTaskList = new TaskList(userId, taskListName);
        TaskListRecord newTaskListRecord = new TaskListRecord(userId, taskListName);
        taskListRepository.save(newTaskListRecord);
        return newTaskList;
    }

    public TaskList updateTaskListName(TaskListCreateRequest request, String userId){
        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
        TaskList updatedList;
        if(taskListRecord.isPresent()){
            updatedList = taskListRepository.updateListName(request.getTaskListName());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list not found for the user.");
        }
        return updatedList;
    }

    public boolean deleteTaskListByUserId(String userId){
        Optional<TaskListRecord> taskListRecord = taskListRepository.findById(userId);
        if(taskListRecord.isPresent()){
            taskListRepository.deleteById(userId);
            return true;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task list not found for the user.");
        }
    }

    private TaskList transformToTaskList(TaskListRecord record){
        return new TaskList(record.getUserId(), record.getTaskListName());
    }
//    createTaskList_taskListAlreadyExistsForId() {
//        // GIVEN
//        String userId = "user";
//        String taskListName = "New List";
//        TaskListCreateRequest request = new TaskListCreateRequest();
//        request.setUserId(userId);
//        request.setTaskListName(taskListName);
//
//        // Simulating an existing task list for the same user ID
//        TaskListRecord existingRecord = new TaskListRecord(userId, "Existing List");
//        when(taskListRepository.findById(userId)).thenReturn(Optional.of(existingRecord));
//
//        // WHEN & THEN
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
//                taskListService.createTaskList(request, userId, taskListName));
//        Assertions.assertEquals(HttpStatus.CONFLICT, exception.getStatus());
//        verify(taskListRepository, never()).save((TaskList) any());
}
*/
