package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.TaskListDao;
import com.kenzie.capstone.service.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collections;

public class LambdaTaskListService {
    private TaskListDao taskListDao;
    private TaskDao taskDao;
    private static final Logger log = LoggerFactory.getLogger(LambdaTaskListService.class);

    @Inject
    public LambdaTaskListService(TaskListDao taskListDao, TaskDao taskDao) {
        this.taskDao = taskDao;
        this.taskListDao = taskListDao;
    }

    //Todo exception handling;
    public TaskListRecord findTaskListByUserId(String userId) {
        return taskListDao.getTaskListByUserId(userId);
    }
}

