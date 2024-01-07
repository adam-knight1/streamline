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

//    @GetMapping("/userId")
//    public ResponseEntity<TaskListResponse> getTaskListById(String userId) {
//        TaskList taskList = taskListService.findTaskListByUserId(userId);
//        if (taskList == null) {
//            return ResponseEntity.notFound().build();
//        }
//        TaskListResponse taskListResponse = new TaskListResponse();
//        taskListResponse.setUserId(taskList.getUserId());
//        taskListResponse.setTaskListName(taskList.getTaskListName());
//        //This may need some more logic
//        taskListResponse.setTasks(taskList.getTasks());
//        return ResponseEntity.ok(taskListResponse);
//    }

    @PostMapping("/create")
    public ResponseEntity<TaskListResponse> createTaskList(@RequestBody TaskListCreateRequest createRequest) {
        TaskListRequest request = new TaskListRequest(createRequest.getUserId(), createRequest.getExistingTaskListName());

        if (createRequest.getUserId() == null) {
            request.setUserId(UUID.randomUUID().toString());
        }

        try {
            TaskListResponse taskListResponse = taskListService.createTaskList(request);

//            com.kenzie.capstone.service.model.TaskListResponse taskListResponseLambda = new com.kenzie.capstone.service.model.TaskListResponse();
//            taskListResponseLambda.setUserId(taskListResponse.getUserId());
//            taskListResponseLambda.setTaskListName(taskListResponse.getTaskListName());
//            taskListResponseLambda.setTasks(Collections.emptyList());

            return ResponseEntity.ok(taskListResponse);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
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
    }

   /* @PutMapping("/userId/updateName")
    public ResponseEntity<TaskListResponse> updateTaskListName(TaskListCreateRequest request, String userId){

omer implemented original code
    @PutMapping("/userId/updateName")
    public ResponseEntity<TaskListResponse> updateTaskListName(TaskListCreateRequest request, String userId){
        TaskList taskList = taskListService.updateTaskListName(request, userId);
       if(taskList == null){
            return ResponseEntity.notFound().build();
        }
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setUserId(taskList.getUserId());
        taskListResponse.setTaskListName(taskList.getTaskListName());
        //This may need some more logic
       taskListResponse.setTasks(Collections.emptyList());
        return ResponseEntity.ok(taskListResponse);
    }


    //i didnt want to delete omers original code. Just wanted to mess with this constructor. -alexis
    @PutMapping("/userId/updateName")
    public ResponseEntity<TaskListResponse> updateTaskListName(@RequestBody TaskListCreateRequest request,
                                                               @PathVariable("userId") String userId){
        String updatedTaskListName = request.getTaskListName();

        TaskList taskList = taskListService.updateTaskListName(request, userId);
        if(taskList == null){
            return ResponseEntity.notFound().build();
        }
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setUserId(taskList.getUserId());
        taskListResponse.setTaskListName(taskList.getTaskListName());
        taskListResponse.setTasks(Collections.emptyList());
        return ResponseEntity.ok(taskListResponse);
    }

    */

    @PutMapping("/{userId}/updateName")
    public ResponseEntity<TaskListResponse> updateTaskListName(@RequestBody TaskListCreateRequest request,
                                                               @PathVariable String userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        TaskListRecord updatedRecord = taskListService.updateTaskListName(request, userId);
        if (updatedRecord == null) {
            return ResponseEntity.notFound().build();
        }
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setUserId(updatedRecord.getUserId());
        taskListResponse.setTaskListName(updatedRecord.getTaskListName());
        taskListResponse.setTasks(Collections.emptyList());
        return ResponseEntity.ok(taskListResponse);

    }

//    @DeleteMapping("/userId/delete")
//    public ResponseEntity<String> deleteTaskListByUserId(String userId) {
//        if (taskListService.deleteTaskListByUserId(userId)) {
//            return ResponseEntity.ok("Task list successfully deleted for user id: " + userId);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
