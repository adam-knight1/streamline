
package com.kenzie.appserver.service;

import com.kenzie.appserver.TaskCreationException;
//import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
//import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
//import com.kenzie.capstone.service.model.TaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
 public class TaskService {
    private TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private LambdaServiceClient lambdaServiceClient = new LambdaServiceClient();

}



