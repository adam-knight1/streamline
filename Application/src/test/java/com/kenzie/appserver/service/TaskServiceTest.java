package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
//    @Test
//    public void addTask_Unsuccessful(){
//
//    }

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
//        int taskId = 123;
//        String updatedTaskName = "Updated Task Name";
//        String updatedTaskDescription = "Updated Task Description";
//
//        when(lambdaServiceClient.updateTask(taskId, updatedTaskName, updatedTaskDescription)).thenReturn(false);
//
//        TaskResponse result = taskService.updateTask(taskId,updatedTaskName, updatedTaskDescription);
//
//        assertNotNull(result);
//        assertEquals(taskId,result.getTaskId());
//        assertEquals(updatedTaskName,result.getTaskName());
//        assertEquals(updatedTaskDescription,result.getTaskDescription());
//        assertEquals("Failed to update task successfully", result.getMessage());
//    }

}