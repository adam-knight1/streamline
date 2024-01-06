package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.TaskListDao;
import com.kenzie.capstone.service.model.*;
import exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {

    /**
     * ------------------------------------------------------------------------
     * expenseService.getExpenseById
     * ------------------------------------------------------------------------
     **/
//Commented out these tests since the example classes no longer exist, kept this for reference - OB

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

//this test passes
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

    //this test passes
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
    //this test passes
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
    //this test passes
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

        TaskListRecord taskListRecord = new TaskListRecord();
        when(taskListDao.getTaskListByUserId(userId)).thenReturn(taskListRecord);

        TaskRecord storedTaskRecord = new TaskRecord();
        when(taskDao.storeTaskData(any(TaskRecord.class))).thenReturn(storedTaskRecord);

        TaskResponse response = lambdaTaskListService.createTask(userId,taskRequest);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertNotNull(response.getTaskId());
        assertEquals(taskRequest.getTaskName(), response.getTaskName());
        assertEquals("",response.getTaskDescription());
        assertFalse(response.isCompleted());

    }

//    Passes
    @Test
    public void updateTaskList_ValidUserId_UpdatesSuccessfully() {
        String userId = "UserId";
        String existingTaskListName = "Existing List";
        String newTaskListName = "Updated List";
        TaskListRequest taskListRequest = new TaskListRequest(userId, newTaskListName, existingTaskListName);

        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(newTaskListName);

        when(taskListDao.getTaskListByTaskListName(userId, existingTaskListName)).thenReturn(taskListRecord);
        when(taskListDao.updateTaskListRecord(userId, existingTaskListName, newTaskListName)).thenReturn(new TaskListResponse(userId,
                newTaskListName));

        TaskListResponse response = lambdaTaskListService.updateTaskList(userId,taskListRequest);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(taskListRequest.getNewTaskListName(), response.getTaskListName());
    }

//    Passes
    @Test
    public void updateTaskList_InvalidUserId_ThrowsException(){
        String userId = "badUserId";
        TaskListRequest taskListRequest = new TaskListRequest(userId, "Task List");

        when(taskListDao.getTaskListByUserId(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            lambdaTaskListService.updateTaskList(userId,taskListRequest);
        });
    }
}

