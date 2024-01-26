package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskAddResponseModel;
import com.kenzie.appserver.service.TaskService;
import com.kenzie.capstone.service.model.GetAllTasksResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kenzie.capstone.service.model.TaskAddRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetAllTasksResponse> getTasksByUserId(@PathVariable String userId) {
        try {
            GetAllTasksResponse tasksResponse = taskService.getTasksByUserId(userId);
            if (tasksResponse.getTasks() == null || tasksResponse.getTasks().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(tasksResponse);
        } catch (Exception e) {
            System.out.println("Error in TaskController while retrieving tasks: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
