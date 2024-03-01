package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import com.kenzie.capstone.service.model.GetTaskListLambdaResponse;



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

    @Test
    void FindTaskListByUserIdTest() throws Exception {
        String userId = "testing123";
        GetTaskListLambdaResponse mockResponse = new GetTaskListLambdaResponse();

        mockResponse.setUserId(userId);
        mockResponse.setTaskListName("Test Task List");
        mockResponse.setTasks(Arrays.asList("Task1", "Task2"));

        when(lambdaServiceClient.findTaskListByUserId(userId)).thenReturn(mockResponse);

        GetTaskListLambdaResponse result = taskListService.findTaskListByUserId(userId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userId, result.getUserId());
        Assertions.assertEquals("Test Task List", result.getTaskListName());
        Assertions.assertTrue(result.getTasks().containsAll(Arrays.asList("Task1", "Task2")));

        verify(lambdaServiceClient, times(1)).findTaskListByUserId(userId);
    }
}