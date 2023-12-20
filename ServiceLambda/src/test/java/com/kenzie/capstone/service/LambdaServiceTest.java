package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ExampleRecord;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/

    private ExampleDao exampleDao;
    private LambdaService lambdaService;

    @BeforeAll
    void setup() {
        this.exampleDao = mock(ExampleDao.class);
        this.lambdaService = new LambdaService(exampleDao);
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

    }