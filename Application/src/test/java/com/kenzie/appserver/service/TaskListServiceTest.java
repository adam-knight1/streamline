package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.TaskListRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskListServiceTest {

    @Autowired
    private TaskListService taskListService;

    @Test
    public void updateTaskList_Exists_Succeeds() {
        // GIVEN
        String userId = UUID.randomUUID().toString();
        String taskListName = "Name";

        TaskListRecord taskListRecord = new TaskListRecord(userId, taskListName);

        String newTaskListName = "NewName";

        // WHEN
        taskListService.updateTaskListName()

        // THEN

    }






}
