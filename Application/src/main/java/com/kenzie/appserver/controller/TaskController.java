package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskAddRequest;
import com.kenzie.appserver.controller.model.TaskAddResponse;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.service.TaskService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskAddResponse> addTask (@RequestBody TaskAddRequest taskAddRequest) {

        try {
            TaskAddResponse taskAddResponse = taskService.addTask(taskAddRequest);
            return ResponseEntity.ok(taskAddResponse);
        } catch (Exception e) {
            System.out.println("Error in task controller");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
