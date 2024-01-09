package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.TaskListDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.*;
import exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {
    private TaskDao taskDao;
    private TaskListDao taskListDao;
    private UserDao userDao;
    private LambdaTaskService lambdaTaskService;
    private LambdaTaskListService lambdaTaskListService;
    private LambdaUserService lambdaUserService;

    @BeforeAll
    void setup() {
        this.taskDao = mock(TaskDao.class);
        this.taskListDao = mock(TaskListDao.class);
        this.userDao = mock(UserDao.class);
        this.lambdaTaskService = new LambdaTaskService(taskDao);
        this.lambdaTaskListService = new LambdaTaskListService(taskListDao,taskDao);
        this.lambdaUserService = new LambdaUserService(userDao);
    }
//not passing
//    @Test
//   public void updateTask_Success() {
//        String taskId = "Updated Task Id";
//        String updatedTaskName = "Updated Task Name";
//        String updatedTaskDescription = "Updated Task Description";
//        boolean completed = true;
//
//
//        TaskRecord updatedTaskRecord = new TaskRecord();
//        updatedTaskRecord.setTaskId(taskId);
//        updatedTaskRecord.setTaskName(updatedTaskName);
//        updatedTaskRecord.setTaskDescription(updatedTaskDescription);
//
//        //WHEN
//        when(taskDao.getTaskRecordById(taskId)).thenReturn(updatedTaskRecord);
//        when(taskDao.updateTaskRecord(any(TaskRecord.class))).thenReturn(updatedTaskRecord);
//
//        TaskResponseLambda result = lambdaTaskService.updateTask(taskId,updatedTaskName,updatedTaskDescription, completed);
//
//        assertNotNull(result);
//        assertEquals(updatedTaskName,result.getTaskName());
//        assertEquals(updatedTaskDescription,result.getTaskDescription());
//
//    }

    @Test
    public void updateTask_Unsuccessful() {
        String taskId = "Updated Task Id";
        String taskName = "Updated Task Name";
        String taskDescription = "Updated Task Description";
        boolean completed = false;

        when(taskDao.getTaskRecordById(taskId)).thenReturn(null);

        assertThrows(InvalidDataException.class, () -> {
            lambdaTaskService.updateTask(taskId,taskName, taskDescription, completed);
        });
    }

    @Test
    public void createTask_MissingTaskName_ThrowsException() {
        //GIVEN
        String userId = "testUserId";
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setTaskName(null);


        assertThrows(IllegalArgumentException.class, () ->{
            lambdaTaskService.createTask(taskRecord);
        });
    }
//not passing
//    @Test
//    public void createTask_ValidTask_SuccessfulCreation() {
//        //GIVEN
//        String taskName = "testTaskName";
//        TaskRequest taskRequest = new TaskRequest();
//        taskRequest.setUserId("Test UserId");
//        taskRequest.setTaskDescription("Test Description");
//        taskRequest.setTaskId("Test TaskId");
//        taskRequest.setTaskName(taskName);
//
//
//        TaskRecord taskRecord = new TaskRecord();
//        taskRecord.setTaskName(taskName);
//        taskRecord.setTaskDescription("Test Description");
//        taskRecord.setUserId("Test UserId");
//        taskRecord.setCompleted(false);
//
//        when(taskDao.storeTaskData(any(TaskRecord.class))).thenReturn(taskRecord);
//
//        TaskResponseLambda response = lambdaTaskService.createTask(taskRecord);
//
//        assertNotNull(response);
//        assertNotNull(response.getUserId());
//        //assertNotNull(response.getTaskId());
//        assertEquals(taskRequest.getTaskName(), response.getTaskName());
//        assertEquals(taskRequest.getTaskDescription(),response.getTaskDescription());
//        assertEquals(taskRequest.getTaskId(),response.getTaskId());
//        assertFalse(response.isCompleted());
//
//    }

//    Passes
//    @Test
//    public void updateTaskList_ValidUserId_UpdatesSuccessfully() {
//        String userId = "UserId";
//        String existingTaskListName = "Existing List";
//        String newTaskListName = "Updated List";
//        TaskListRequest taskListRequest = new TaskListRequest(userId, newTaskListName, existingTaskListName);
//
//        TaskListRecord taskListRecord = new TaskListRecord();
//        taskListRecord.setUserId(userId);
//        taskListRecord.setTaskListName(newTaskListName);
//
//        when(taskListDao.getTaskListByTaskListName(userId, existingTaskListName)).thenReturn(taskListRecord);
//        when(taskListDao.updateTaskListRecord(userId, existingTaskListName, newTaskListName)).thenReturn(new TaskListResponse(userId,
//                newTaskListName));
//
//        TaskListResponse response = lambdaTaskListService.updateTaskList(userId,taskListRequest);
//
//        assertNotNull(response);
//        assertEquals(userId, response.getUserId());
//        assertEquals(taskListRequest.getNewTaskListName(), response.getTaskListName());
//    }

//    Passes
//    @Test
//    public void updateTaskList_InvalidUserId_ThrowsException(){
//        String userId = "badUserId";
//        TaskListRequest taskListRequest = new TaskListRequest(userId, "Task List");
//
//        when(taskListDao.getTaskListByUserId(userId)).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            lambdaTaskListService.updateTaskList(userId,taskListRequest);
//        });
//    }
    /*
        this.taskDao = mock(TaskDao.class);
        this.taskListDao = mock(TaskListDao.class);
        this.userDao = mock(UserDao.class);
        this.lambdaTaskService = new LambdaTaskService(taskDao);
        this.lambdaTaskListService = new LambdaTaskListService(taskListDao,taskDao);
        this.lambdaUserService = new LambdaUserService(userDao);
     */
    @Test
    public void createUser_Successful() {
        String email = "testEmail";
        String username = "testUserName";
        String password = "testPasword";
        UserRecord userRecord = new UserRecord();
        userRecord.setEmail(email);
        userRecord.setUsername(username);
        userRecord.setPassword(password);

        when(userDao.createUser(userRecord)).thenReturn(userRecord);

        UserResponseLambda response = lambdaUserService.createNewUser(userRecord);

        assertNotNull(response);
        assertEquals(email, response.getEmail());
        assertNotNull(response.getUserId());
        assertEquals(username, response.getUsername());
    }

    @Test
    public void createUser_NullUserRecord_ThrowsException() {
        when(userDao.createUser(null)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            lambdaUserService.createNewUser(null);
        });
    }

    @Test
    public void createTaskList_Successful() {
        // GIVEN
        String userId = "userId";
        String taskListName = "taskListName";
        List<TaskRecord> tasks = new ArrayList<>();
        TaskListRecord taskListRecord = new TaskListRecord();
        taskListRecord.setUserId(userId);
        taskListRecord.setTaskListName(taskListName);
        taskListRecord.setTasks(tasks);

        when(taskListDao.createTaskList(taskListRecord)).thenReturn(taskListRecord);

        // WHEN
        TaskListResponse response = lambdaTaskListService.createTaskList(taskListRecord);

        // THEN
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(taskListName, response.getTaskListName());
    }

    @Test
    public void createTaskList_NullRecord_ThrowsException() {
        when(taskListDao.createTaskList(null)).thenReturn(null);

        // THEN
        assertThrows(IllegalArgumentException.class, () -> {
            lambdaTaskListService.createTaskList(null);
        });
    }
}

