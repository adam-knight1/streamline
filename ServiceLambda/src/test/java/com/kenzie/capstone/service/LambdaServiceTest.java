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
//
//     @Test
//     void addTaskToTaskListTest() {
//         TaskDao taskDao = mock(TaskDao.class);
//         TaskService taskService = new TaskService(taskDao);
//
//         //Given
//         String userId = "testUserId";
//         String taskListName = "testTaskList";
//         TaskRequest taskRequest = new TaskRequest();
//
//         taskRequest.setName("Test task");
//         taskRequest.setDescription("This is a test description");
//
//         TaskRecord expectedTaskRecord = new TaskRecord();
//         expectedTaskRecord.setUserId(userId);
//         expectedTaskRecord.setTaskListName(taskListName);
//         expectedTaskRecord.setTaskId(anyString());
//         expectedTaskRecord.setName(taskRequest.getName());
//         expectedTaskRecord.setDescription(taskRequest.getDescription());
//
//         //When
//         when(taskDao.setTaskData(any(TaskRecord.class))).thenReturn(expectedTaskRecord);
//         TaskResponse result = taskService.addTaskToTaskList(userId, taskListName, taskRequest);
//
//         //then
//         assertEquals(userId, result.getUserId());
//         assertEquals(taskListName, result.getTaskListName());
//         assertNotNull(result.getTaskId());
//         assertEquals(taskRequest.getName(), result.getName());
//         assertEquals(taskRequest.getDescription(), result.getDescription());
//     }

    @Test
   public void updateTask_ValidTaskId() {
        String taskId = UUID.randomUUID().toString();
        TaskRequest validTaskRequest = new TaskRequest();
        validTaskRequest.setTaskId(taskId);
        validTaskRequest.setTaskName("Updated Task Name");
        validTaskRequest.setTaskDescription("Updated Task Description");

        TaskRecord updatedTaskRecord = new TaskRecord();
        updatedTaskRecord.setTaskId(taskId);
        updatedTaskRecord.setTaskName(validTaskRequest.getTaskName());
        updatedTaskRecord.setTaskDescription(validTaskRequest.getTaskDescription());

        //WHEN
        when(taskDao.getTaskRecordById(taskId)).thenReturn(updatedTaskRecord);
        when(taskDao.updateTaskRecord(any(TaskRecord.class))).thenReturn(updatedTaskRecord);

        TaskResponse result = lambdaTaskService.updateTask(taskId,validTaskRequest);

        assertNotNull(result);
        assertEquals(validTaskRequest.getTaskName(),result.getTaskName());
        assertEquals(validTaskRequest.getTaskDescription(), result.getTaskDescription());

    }

    @Test
    public void updateTask_InvalidTaskId() {
        String invalidTaskId = "";
        TaskRequest invalidTaskRequest = new TaskRequest();
        invalidTaskRequest.setTaskId(invalidTaskId);
        invalidTaskRequest.setTaskName("invalid Task Name");
        invalidTaskRequest.setTaskDescription("Invalid Task Description");

        try {
            lambdaTaskService.updateTask(invalidTaskId,invalidTaskRequest);
            fail("Expected an InvalidDataException to be thrown");
        } catch (InvalidDataException exception){
            assertEquals("Request must contain a valid task ID", exception.getMessage());
        }
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

