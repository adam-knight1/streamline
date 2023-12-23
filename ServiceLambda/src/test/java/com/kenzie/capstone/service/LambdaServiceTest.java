package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/
//Commented out these tests since the example classes no longer exist, kept this for reference - OB
  /*

    private ExampleDao exampleDao;
    private LambdaService lambdaService;
    private TaskDao taskDao;
    private LambdaTaskService lambdaTaskService;

    @BeforeAll
    void setup() {
        //this.exampleDao = mock(ExampleDao.class);
        //this.lambdaService = new LambdaService(exampleDao);
        this.taskDao = mock(TaskDao.class);
        this.lambdaTaskService = new LambdaTaskService(taskDao);
    }
    @Test
    void addTaskToTaskListTest() {
        TaskDao taskDao = mock(TaskDao.class);
        TaskService taskService = new TaskService(taskDao);

        //Given
        String userId = "testUserId";
        String taskListName = "testTaskList";
        TaskRequest taskRequest = new TaskRequest();

        taskRequest.setName("Test task");
        taskRequest.setDescription("This is a test description");

        TaskRecord expectedTaskRecord = new TaskRecord();
        expectedTaskRecord.setUserId(userId);
        expectedTaskRecord.setTaskListName(taskListName);
        expectedTaskRecord.setTaskId(anyString());
        expectedTaskRecord.setName(taskRequest.getName());
        expectedTaskRecord.setDescription(taskRequest.getDescription());

        //When
        when(taskDao.setTaskData(any(TaskRecord.class))).thenReturn(expectedTaskRecord);
        TaskResponse result = taskService.addTaskToTaskList(userId, taskListName, taskRequest);

        //then
        assertEquals(userId, result.getUserId());
        assertEquals(taskListName, result.getTaskListName());
        assertNotNull(result.getTaskId());
        assertEquals(taskRequest.getName(), result.getName());
        assertEquals(taskRequest.getDescription(), result.getDescription());
    }
    @Test
    void updateTaskTest(){
        //GIVEN
        String taskId = "sampleTaskId";
        String userId = "sampleUserId";
        String taskName = "sampleTaskName";
        TaskRequest taskRequest = new TaskRequest(userId, taskName);

        TaskRecord existingTask = new TaskRecord();
        existingTask.setTaskId(taskId);
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setTaskName(taskRequest.getTaskName());
        existingTask.setUserId(userId);
        existingTask.setCompleted(taskRequest.isCompleted());
        existingTask.setTaskName(taskRequest.getTaskName());

        TaskRecord expectedUpdatedTask = new TaskRecord();
        expectedUpdatedTask.setUserId(userId);
        expectedUpdatedTask.setTaskId(taskId);
        expectedUpdatedTask.setTaskName("Updated Task Name");
        expectedUpdatedTask.setDescription("Updated Task Description");
        expectedUpdatedTask.setCompleted(true);
        expectedUpdatedTask.setTaskName("Updated task List Name");

        //WHEN
        when(lambdaTaskService.updateTask(anyString(),any(TaskRequest.class)))
                .thenReturn(expectedUpdatedTask);

        TaskRecord updatedTask = lambdaTaskService.updateTask(taskId,taskRequest);

        //THEN
        assertNotNull(updatedTask);
        assertEquals(userId, updatedTask.getUserId());
        assertEquals(taskId,updatedTask.getTaskId());
        assertEquals("Updated Name", updatedTask.getTaskName());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertTrue(updatedTask.isCompleted());
        assertEquals(taskName,updatedTask.getTaskName());

    }
*/
    }