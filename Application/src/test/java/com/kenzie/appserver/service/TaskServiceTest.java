package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testAddTask(){
        //GIVEN
        Task task = new Task("Example Task", "Description of task", true);

        //WHEN
        Task addedTask = taskService.addTask(task);

        //THEN
        assertNotNull(addedTask);
        assertNotNull(addedTask.getTaskId());
        assertEquals("Example Task", addedTask.getTaskName());
    }

    @Test
    public void testDeleteTask(){
        //GIVEN
        Task task = new Task("Task to Delete", "Description of task", true);

        //WHEN
        Task addedTask = taskService.addTask(task);
        boolean result = taskService.deleteTask(addedTask.getTaskId());

        assertTrue(result);

        Task deletedTask = taskService.updateTaskStatus(addedTask.getTaskId(), true);
        assertNotNull(deletedTask);

    }
}