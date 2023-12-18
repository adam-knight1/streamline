package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testAddTask(){
        Task task = new Task("Example Task", "Description of task", "pending");
        Task addedTask = taskService.addTask(task);
        assertNotNull(addedTask);
        assertNotNull(addedTask.getTaskId());
        assertEquals("Example Task", addedTask.getTaskName());
    }






}