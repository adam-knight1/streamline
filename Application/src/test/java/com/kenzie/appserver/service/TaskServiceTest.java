package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TaskServiceTest {
    private TaskRepository taskRepository;
    private TaskService taskService;
    private TaskListRepository taskListRepository;
    private TaskListService taskListService;
    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setup(){
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository,taskListRepository,lambdaServiceClient);
        taskListRepository = mock(TaskListRepository.class);
        taskListService = new TaskListService(taskListRepository,lambdaServiceClient);
    }
    @Test
    void addTask_Successful(){
        //GIVEN
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setTaskDescription("Description of task");
        taskRecord.setTaskName("Sample taskName");
        taskRecord.setCompleted(true);

        when(taskRepository.save(any(TaskRecord.class))).thenReturn(taskRecord);

        TaskRecord addedTask = taskService.addTask(taskRecord);

        assertNotNull(addedTask);
        assertEquals("Sample taskName", addedTask.getTaskName());
        assertEquals("Description of task",addedTask.getTaskDescription());
        assertTrue(addedTask.isCompleted());

    }

//    @Test
//    public void testDeleteTask(){
//        //GIVEN
//        Task task = new Task("Task to Delete", "Description of task", true);
//
//        //WHEN
//
//    }
}