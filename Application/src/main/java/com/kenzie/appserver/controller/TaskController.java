package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.TaskService;
import com.kenzie.capstone.service.model.CompleteTaskRequest;
import com.kenzie.capstone.service.model.GetAllTasksResponse;
import com.kenzie.capstone.service.model.TaskAddResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kenzie.capstone.service.model.TaskAddRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final Logger logger = LogManager.getLogger();

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * Endpoint to add a new task to the system.
     * @param taskAddRequest the task to be added, encapsulated as a request body.
     * @return ResponseEntity containing the added task details or an error status.
     */
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

    /**
     * Retrieves all tasks associated with a specific user ID.
     * @param userId the unique identifier of the user.
     * @return ResponseEntity containing all tasks for the user or a status indicating no content or an error.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetAllTasksResponse> getTasksByUserId(@PathVariable String userId) {
        try {
            logger.info("Received request to get tasks for userId: {}", userId);

            GetAllTasksResponse tasksResponse = taskService.getTasksByUserId(userId);

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
    /**
     * Marks a task as complete based on a given user ID and task ID.
     * @param completeTaskRequest contains the user ID and task ID of the task to be completed.
     * @return ResponseEntity with HTTP status indicating success or internal server error.
     */
    @PostMapping("/complete")
    public ResponseEntity<?> completeTask(@RequestBody CompleteTaskRequest completeTaskRequest) {
        try {
            logger.info("Received request to complete task with userId: {} and taskId: {}", completeTaskRequest.getUserId(), completeTaskRequest.getTaskId());

            taskService.completeTask(completeTaskRequest.getUserId(), completeTaskRequest.getTaskId());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error completing task for userId: {} and taskId: {}", completeTaskRequest.getUserId(), completeTaskRequest.getTaskId(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
