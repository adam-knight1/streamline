package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    private TaskService taskService;

    @Captor
    private ArgumentCaptor<TaskRecord> taskRecordCaptor;

    @Test
    public void AddTask_Sucessful(){
        //GIVEN
        Task task = new Task("Example Task", "Description of task", true);

        //WHEN
        //Task addedTask = taskService.addTask(task);

        //THEN

    }

    @Test
    public void testDeleteTask(){
        //GIVEN
        Task task = new Task("Task to Delete", "Description of task", true);

        //WHEN

    }
}