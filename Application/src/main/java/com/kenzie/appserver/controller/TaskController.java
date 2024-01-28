package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskAddResponseModel;
import com.kenzie.appserver.service.TaskService;
import com.kenzie.capstone.service.model.GetAllTasksResponse;
import com.kenzie.capstone.service.model.TaskAddResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kenzie.capstone.service.model.TaskAddRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final Logger logger = LogManager.getLogger();

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<TaskAddResponse> addTask (@RequestBody TaskAddRequest taskAddRequest) {

        try {
            TaskAddResponse taskAddResponse = taskService.addTask(taskAddRequest);
            return ResponseEntity.ok(taskAddResponse);
        } catch (Exception e) {
            System.out.println("Error in task controller");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetAllTasksResponse> getTasksByUserId(@PathVariable String userId) {
        try {
            logger.info("Received request to get tasks for userId: {}", userId);

            GetAllTasksResponse tasksResponse = taskService.getTasksByUserId(userId);

            // Log the response from TaskService
            logger.info("TasksResponse from TaskService for userId {}: {}", userId, tasksResponse);

            if (tasksResponse.getTasks() == null || tasksResponse.getTasks().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(tasksResponse);
        } catch (Exception e) {
            logger.error("Error in TaskController while retrieving tasks for userId {}: ", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
