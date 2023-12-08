package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.service.model.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListService {
    private TaskListRepository taskListRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public TaskList createTaskList(CreateTaskListRequest createTaskListRequest) {
        TaskList taskList = new TaskList();
        taskList.setTaskListName(createTaskListRequest);
    }
}
