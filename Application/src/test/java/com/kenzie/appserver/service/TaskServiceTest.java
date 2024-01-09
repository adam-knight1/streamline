package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.TaskCreationException;
import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.TaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListRepository taskListRepository;
    @Mock
    private LambdaServiceClient lambdaServiceClient;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void add_Task_Successful() {
        String userId = "user";
        String taskId = "sampleTaskId";
        String taskName = "sampleName";
        String taskDescription = "sampleDescription";
        boolean completed = false;

        TaskRecord sampleTask = new TaskRecord();
        sampleTask.setTaskId(taskId);
        sampleTask.setTaskName(taskName);
        sampleTask.setTaskDescription(taskDescription);
        sampleTask.setUserId(userId);
        sampleTask.setCompleted(completed);

        when(taskRepository.save(any(TaskRecord.class))).thenReturn(sampleTask);

        TaskRecord createdTask = taskService.addTask(sampleTask);

        verify(taskRepository, times(1)).save(any(TaskRecord.class));

        assertNotNull(createdTask);
        assertEquals(sampleTask.getTaskId(),createdTask.getTaskId());
        assertEquals(sampleTask.getTaskName(),createdTask.getTaskName());
        assertEquals(sampleTask.getTaskDescription(),createdTask.getTaskDescription());
        assertEquals(sampleTask.getUserId(),createdTask.getUserId());
        assertEquals(sampleTask.isCompleted(),createdTask.isCompleted());
    }
    @Test
    public void addTask_Unsuccessful(){
        String userId = "user";
        String taskId = "sampleTaskId";
        String taskName = "sampleName";
        String taskDescription = "sampleDescription";
        boolean completed = false;

        TaskRecord sampleTask = new TaskRecord();
        sampleTask.setTaskId(taskId);
        sampleTask.setTaskName(taskName);
        sampleTask.setTaskDescription(taskDescription);
        sampleTask.setUserId(userId);
        sampleTask.setCompleted(completed);

        when(taskRepository.save(any(TaskRecord.class))).thenThrow(new RuntimeException("Failed to add task"));

        assertThrows(RuntimeException.class, () -> {
            taskService.addTask(sampleTask);
        });

        verify(taskRepository, times(1)).save(any(TaskRecord.class));


    }

    @Test
    public void updateTask_Successful() throws JsonProcessingException {
        String taskId = "sampleTaskId";
        String updatedTaskName = "Updated Task Name";
        String updatedTaskDescription = "Updated Task Description";

        when(lambdaServiceClient.updateTask(taskId, updatedTaskName, updatedTaskDescription)).thenReturn(true);

        TaskResponse result = taskService.updateTask(taskId,updatedTaskName, updatedTaskDescription);

        assertNotNull(result);
        assertEquals(taskId,result.getTaskId());
        assertEquals(updatedTaskName,result.getTaskName());
        assertEquals(updatedTaskDescription,result.getTaskDescription());
        assertEquals("Task updated successfully", result.getMessage());
    }
//    @Test
//    public void updateTask_Unsuccessful() throws JsonProcessingException {
//        String taskId = "sampleTaskId";
//        String updatedTaskName = "Updated Task Name";
//        String updatedTaskDescription = "Updated Task Description";
//
//        TaskResponse sampleTask = new TaskResponse();
//        sampleTask.setTaskId(taskId);
//        sampleTask.setTaskName(updatedTaskName);
//        sampleTask.setTaskDescription(updatedTaskDescription);
//
//
//        when(lambdaServiceClient.updateTask(taskId, updatedTaskName, updatedTaskDescription))
//                .thenThrow(new RuntimeException("Failed to update a task"));
//
//        assertThrows(RuntimeException.class, () -> {
//            taskService.updateTask(sampleTask);
//        });
//        when(lambdaServiceClient.updateTask(any(TaskResponse.class)).t.save(any(TaskRecord.class))).thenThrow(new RuntimeException("Failed to add task"));
//
//        assertThrows(RuntimeException.class, () -> {
//            taskService.addTask(sampleTask);
//        });
//
//        verify(taskRepository, times(1)).save(any(TaskRecord.class));
//
//    }
    //test passes
    @Test
    public void createTask_Successful() throws JsonProcessingException, TaskCreationException {
        String userId = "user";
        String taskId = "sampleTaskId";
        String taskName = "sampleName";
        String taskDescription = "sampleDescription";
        boolean completed = false;

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTaskId(taskId);
        taskRequest.setTaskName(taskName);
        taskRequest.setTaskDescription(taskDescription);
        taskRequest.setUserId(userId);
        taskRequest.setCompleted(completed);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskId(taskId);
        taskResponse.setTaskName(taskName);
        taskResponse.setTaskDescription(taskDescription);
        taskResponse.setUserId(userId);
        taskResponse.setCompleted(completed);

        when(lambdaServiceClient.createTask(taskRequest)).thenThrow(new TaskCreationException(taskResponse,
                "failed to create task"));

        assertThrows(TaskCreationException.class, () ->{
            taskService.createTask(taskRequest);
        });

        verify(lambdaServiceClient,times(1)).createTask(taskRequest);
    }
    //passing
    @Test
    public void createTask_Unsuccessful() throws TaskCreationException, JsonProcessingException {
        String userId = "user";
        String taskId = "sampleTaskId";
        String taskName = "sampleName";
        String taskDescription = "sampleDescription";
        boolean completed = false;

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTaskId(taskId);
        taskRequest.setTaskName(taskName);
        taskRequest.setTaskDescription(taskDescription);
        taskRequest.setUserId(userId);
        taskRequest.setCompleted(completed);

        when(lambdaServiceClient.createTask(taskRequest)).thenThrow(new RuntimeException("Failed to create task"));

        assertThrows(TaskCreationException.class, () ->{
            taskService.createTask(taskRequest);
        });

        verify(lambdaServiceClient,times(1)).createTask(taskRequest);

    }

}