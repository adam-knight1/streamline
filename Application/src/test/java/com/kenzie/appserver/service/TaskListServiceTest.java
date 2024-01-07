package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.model.TaskList;
import com.kenzie.capstone.service.client.ApiGatewayException;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.TaskListRequest;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class TaskListServiceTest {
    @Mock
    private TaskListRepository taskListRepository;
    @Mock
    private LambdaServiceClient lambdaServiceClient;
    private TaskListService taskListService;
    @Captor
    private ArgumentCaptor<TaskListRecord> taskListRecordCaptor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        taskListService = new TaskListService(taskListRepository, lambdaServiceClient);
    }

    /** ------------------------------------------------------------------------
     *  taskListService.createTaskList
     *  ------------------------------------------------------------------------ **/

    @Test
    void createTaskList_happyCase() throws JsonProcessingException {
        // GIVEN
        String userId = "user";
        String taskListName = "Paul's Tasks";
        TaskListRecord record = new TaskListRecord();
        record.setUserId(userId);
        record.setTaskListName(taskListName);
        TaskListRequest request = new TaskListRequest(userId, taskListName);

        when(taskListRepository.findById(userId)).thenReturn(Optional.of(record));
        when(taskListRepository.save(record)).thenReturn(record);

        // WHEN
        TaskListResponse taskListResponse = taskListService.createTaskList(request);

        // THEN
        Assertions.assertNotNull(taskListResponse);
        Assertions.assertEquals(userId, taskListResponse.getUserId());
        Assertions.assertEquals(taskListName, taskListResponse.getTaskListName());
        Assertions.assertEquals(Collections.emptyList(), taskListResponse.getTasks());
    }

//    @Test
//    void createTaskList_nonExistingUser_fails() throws JsonProcessingException {
//        // GIVEN
//        String userId = "nonExistingUser";
//        String taskListName = "Chores";
//        TaskListRequest request = new TaskListRequest(userId, taskListName);
//
//        when(taskListService.createTaskList(request)).thenThrow(Exception.class);
//
//        // WHEN
//        try{
//            taskListService.createTaskList(request);
//        }catch(Exception ignored){}
//    }

    /** ------------------------------------------------------------------------
     *  taskListService.updateTaskList
     *  ------------------------------------------------------------------------ **/

    @Test
    public void updateTaskList_Exists_Succeeds() {
        // GIVEN
        String userId = "user";
        String taskListName = "Name";
        String newTaskListName = "NewName";
        TaskListCreateRequest request = new TaskListCreateRequest();
        request.setUserId(userId);
        request.setTaskListName(newTaskListName);

        // simulating an existing tasklist for the same user
        TaskListRecord existingRecord = new TaskListRecord(userId, taskListName);
        TaskListRecord updatedTaskListRecord = new TaskListRecord(userId, newTaskListName);

        when(taskListRepository.findById(userId)).thenReturn(Optional.of(existingRecord));
        when(taskListRepository.save(updatedTaskListRecord)).thenReturn(updatedTaskListRecord);

        // WHEN
        TaskListRecord updatedTasklist = taskListService.updateTaskListName(request, userId);

        // THEN
        Assertions.assertNotNull(updatedTasklist);
        Assertions.assertEquals(newTaskListName, updatedTasklist.getTaskListName());
    }

    @Test
    public void updateTaskList_DoesNotExist_Fails() {
        // GIVEN
        String userId = "user";
        String taskListName = "Name";
        String newTaskListName = "NewName";
        TaskListCreateRequest request = new TaskListCreateRequest();
        request.setUserId(userId);
        request.setTaskListName(newTaskListName);

        when(taskListRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN & THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                taskListService.updateTaskListName(request, userId));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}