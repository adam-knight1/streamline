package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskAddResponseModel;
import com.kenzie.appserver.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kenzie.capstone.service.model.TaskAddRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<TaskAddResponseModel> addTask (@RequestBody TaskAddRequest taskAddRequest) {

        try {
            TaskAddResponseModel taskAddResponse = taskService.addTask(taskAddRequest);
            return ResponseEntity.ok(taskAddResponse);
        } catch (Exception e) {
            System.out.println("Error in task controller");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
