package com.kenzie.capstone.service;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.TaskListDao;
import com.kenzie.capstone.service.model.TaskListRecord;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;
import exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {
    private TaskDao taskDao;
    private TaskListDao taskListDao;
    private LambdaTaskService lambdaTaskService;
    private LambdaTaskListService lambdaTaskListService;

    @BeforeAll
    void setup() {
        this.taskDao = mock(TaskDao.class);
        this.lambdaTaskService = new LambdaTaskService(taskDao);
        this.taskListDao = mock(TaskListDao.class);
        this.lambdaTaskListService = new LambdaTaskListService(taskListDao,taskDao);
    }

    @Test
   public void updateTask_Success() {
        int taskId = 123;
        String updatedTaskName = "Updated Task Name";
        String updatedTaskDescription = "Updated Task Description";


        TaskRecord updatedTaskRecord = new TaskRecord();
        updatedTaskRecord.setTaskId(taskId);
        updatedTaskRecord.setTaskName(updatedTaskName);
        updatedTaskRecord.setTaskDescription(updatedTaskDescription);

        //WHEN
        when(taskDao.getTaskRecordById(taskId)).thenReturn(updatedTaskRecord);
        when(taskDao.updateTaskRecord(any(TaskRecord.class))).thenReturn(updatedTaskRecord);

        TaskResponse result = lambdaTaskService.updateTask(taskId,updatedTaskName,updatedTaskDescription);

        assertNotNull(result);
        assertEquals(updatedTaskName,result.getTaskName());
        assertEquals(updatedTaskDescription,result.getTaskDescription());

    }

    @Test
    public void updateTask_Unsuccessful() {
        int taskId = 123;
        String taskName = "Updated Task Name";
        String taskDescription = "Updated Task Description";

        when(taskDao.getTaskRecordById(taskId)).thenReturn(null);

        assertThrows(InvalidDataException.class, () -> {
            lambdaTaskService.updateTask(taskId,taskName, taskDescription);
        });
    }

    @Test
    public void createTask_MissingTaskName_ThrowsException() {
        //GIVEN
        String userId = "testUserId";
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTaskName(null);

        assertThrows(IllegalArgumentException.class, () ->{
            lambdaTaskListService.createTask(userId,taskRequest);
        });
    }

    @Test
    public void createTask_NonExistingTaskList_ThrowsException() {
    //GIVEN
        String userId = "nonExistingUserId";
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTaskName("Test Task");

        when(taskListDao.getTaskListByUserId(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            lambdaTaskListService.createTask(userId,taskRequest);
        });
    }
    @Test
    public void createTask_ValidTask_SuccessfulCreation() {
        //GIVEN
        String userId = "testUserId";
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTaskName("Test Task");
        taskRequest.setTaskDescription("Test Description");

        TaskListRecord taskListRecord = new TaskListRecord();
        when(taskListDao.getTaskListByUserId(userId)).thenReturn(taskListRecord);

        TaskRecord storedTaskRecord = new TaskRecord();
        when(taskDao.storeTaskData(any(TaskRecord.class))).thenReturn(storedTaskRecord);

        TaskResponse response = lambdaTaskListService.createTask(userId,taskRequest);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertNotNull(response.getTaskId());
        assertEquals(taskRequest.getTaskName(), response.getTaskName());
        assertEquals(taskRequest.getTaskDescription(),response.getTaskDescription());
        assertFalse(response.isCompleted());

    }
}

