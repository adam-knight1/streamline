package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.TaskListService;
import com.kenzie.capstone.service.model.GetTaskListLambdaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   /* @GetMapping("/{userId}")
    public ResponseEntity<GetTaskListLambdaResponse> getTaskListByUserId(@PathVariable("userId") String userId) {
        System.out.println("Received request to find taskList with userId: " + userId);
        try {
           GetTaskListLambdaResponse getTaskListLambdaResponse = taskListService.findTaskListByUserId(userId);
            if (getTaskListLambdaResponse == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(getTaskListLambdaResponse);
        } catch (Exception e) {
            System.out.println("Error in fetching user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
