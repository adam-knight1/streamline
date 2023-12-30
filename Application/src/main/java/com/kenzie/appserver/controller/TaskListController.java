package com.kenzie.appserver.controller;

/*
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.service.TaskListService;
import com.kenzie.appserver.service.model.TaskList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/taskList")
public class TaskListController {
    private TaskListService taskListService;

    TaskListController(TaskListService taskListService){this.taskListService = taskListService;}

    @GetMapping("/userId")
    public ResponseEntity<TaskListResponse> getTaskListById(String userId){
        TaskList taskList = taskListService.findTaskListByUserId(userId);
        if(taskList == null){
            return ResponseEntity.notFound().build();
        }
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setUserId(taskList.getUserId());
        taskListResponse.setTaskListName(taskList.getTaskListName());
        //This may need some more logic
        taskListResponse.setTasks(taskList.getTasks());
        return ResponseEntity.ok(taskListResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskListResponse> createTaskList(TaskListCreateRequest request,
                                                           String userId, String taskListName){
        TaskList taskList = taskListService.createTaskList(request, userId, taskListName);
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

    @DeleteMapping("/userId/delete")
    public ResponseEntity<String> deleteTaskListByUserId(String userId){
        if(taskListService.deleteTaskListByUserId(userId)){
            return ResponseEntity.ok("Task list successfully deleted for user id: " + userId);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}*/
