package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.model.Get;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.TaskListService;
import com.kenzie.capstone.service.model.GetTaskListLambdaResponse;
import com.kenzie.capstone.service.model.TaskListRequest;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/taskList")
public class TaskListController {

    private TaskListService taskListService;

    TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    /**
     * Retrieves the task list associated with a specific user ID.
     * @param userId the unique identifier of the user whose task list is to be retrieved.
     * @return ResponseEntity containing the task list or a status indicating not found or an internal server error.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<GetTaskListLambdaResponse> getTaskListByUserId(@PathVariable("userId") String userId) {
        System.out.println("Received request to find taskList with userId: " + userId);
        try {
            //This line makes the call to taskListService, which calls the corresponding method in
            // lambda service client, and returns the new get task list response DTO from the lambda service package.
           GetTaskListLambdaResponse getTaskListLambdaResponse = taskListService.findTaskListByUserId(userId);
            if (getTaskListLambdaResponse == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(getTaskListLambdaResponse);
        } catch (Exception e) {
            System.out.println("Error in fetching user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
