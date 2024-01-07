package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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


//    @Test
//    void createTaskList_happyCase() {
//        // GIVEN
//        String userId = "user";
//        String taskListName = "Paul's Tasks";
//        TaskListCreateRequest request = new TaskListCreateRequest();
//        request.setUserId(userId);
//        request.setTaskListName(taskListName);
//
//        when(taskListRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // WHEN
//        TaskList createdTaskList = taskListService.createTaskList(request, userId, taskListName);
//
//        // THEN
//        Assertions.assertNotNull(createdTaskList);
//        Assertions.assertEquals(userId, createdTaskList.getUserId());
//        Assertions.assertEquals(taskListName, createdTaskList.getTaskListName());
//        Assertions.assertEquals(Collections.emptyList(), createdTaskList.getTasks());
//        verify(taskListRepository, times(1)).save(taskListRecordCaptor.capture());
//    }
//
//    @Test
//    void createTaskList_InvalidUserId() {
//        // GIVEN
//        String userId = "user";
//        String taskListName = "MyTasks";
//        TaskListCreateRequest request = new TaskListCreateRequest();
//        // Set a different user id
//        request.setUserId("differentUserId");
//
//        // WHEN & THEN
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                taskListService.createTaskList(request, userId, taskListName));
//        Assertions.assertEquals("Provided user id does not match the user id in the request.", exception.getMessage());
//        //Verify no interactions with repository
//        verifyZeroInteractions(taskListRepository);
//    }

//    @Test
//    void createTaskList_taskListAlreadyExistsForId() {
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
//    }
//
//
///** ------------------------------------------------------------------------
//     *  taskListService.updateTaskList
//     *  ------------------------------------------------------------------------ **/

//    not passing
     @Test
    public void updateTaskList_Exists_Succeeds() {
        // GIVEN
        String userId = "user";
        String taskListName = "Name";
        String newTaskListName = "NewName";
        TaskListCreateRequest request = new TaskListCreateRequest();
        request.setUserId(userId);
        request.setExistingTaskListName(newTaskListName);

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

//    passing
 @Test
    public void updateTaskList_DoesNotExist_Fails() {
        // GIVEN
        String userId = "user";
        String taskListName = "Name";
        String newTaskListName = "NewName";
        TaskListCreateRequest request = new TaskListCreateRequest();
        request.setUserId(userId);
        request.setExistingTaskListName(newTaskListName);

        when(taskListRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN & THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                taskListService.updateTaskListName(request, userId));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}