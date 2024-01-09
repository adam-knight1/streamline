package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.TaskListService;
import com.kenzie.appserver.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskListService taskListService;

    @Autowired
    public TaskController(TaskService taskService, TaskListService taskListService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
    }

//    @GetMapping
//    public List<TaskRecord> getAllTasks() {
//        return taskService.getAllTasks();
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<TaskRecord> createTask (@RequestBody TaskRecord task){
//        TaskRecord createdTask = taskService.addTask(task);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
//   }

//    @PostMapping("/add")
//    public ResponseEntity<TaskRecord> addTask(@RequestBody TaskRecord task){
//        TaskRecord addedTask = taskService.addTask(task);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addedTask);
//    }
//    @PostMapping("/create")
//    public ResponseEntity<TaskResponseLambda> createTask (@RequestBody TaskCreateRequest taskCreateRequest){
//        TaskRequest taskRequest = new TaskRequest();
//        taskRequest.setTaskDescription(taskCreateRequest.getTaskDescription());
//        taskRequest.setTaskId(taskCreateRequest.getTaskId());
//        taskRequest.setTaskName(taskCreateRequest.getTaskName());
//        if (taskRequest.getTaskName() == null){
//            taskRequest.setTaskName(UUID.randomUUID().toString());
//        }
//        try {
//            TaskResponse taskResponse = taskService.createTask(taskRequest);
//
//            TaskResponseLambda taskResponseLambda = new TaskResponseLambda();
//            taskResponseLambda.setTaskName(taskResponse.getTaskName());
//            taskResponseLambda.setTaskDescription(taskResponse.getTaskDescription());
//            taskResponseLambda.setTaskId(taskResponse.getTaskId());
//
//            return ResponseEntity.ok(taskResponseLambda);
//        }catch (Exception e){
//            System.out.println("something went wrong: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }



//    //update task status by taskId
//    @PostMapping("/{taskId}/update-status")
//    public ResponseEntity<TaskRecord> updateTaskStatus(@PathVariable String taskId, @RequestParam boolean newStatus) {
//        TaskRecord updatedTask = taskService.updateTaskStatus(taskId, newStatus);
//        if (updatedTask != null) {
//            return ResponseEntity.ok(updatedTask);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @DeleteMapping("/{taskId}/delete")
//    public ResponseEntity<String > deleteTask (@PathVariable String taskId) {
//        if(taskService.deleteTask(taskId)) {
//            return ResponseEntity.ok("Task successfully deleted ");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PostMapping("/task/update")
//    public ResponseEntity<TaskResponse> updateTask(//@PathVariable String taskId,
//                                                   @RequestParam String taskName,
//                                                   @RequestParam String taskDescription){
//
//        String updatedTaskDescription = null;
//        TaskResponse taskResponse = taskService.updateTask(taskName,taskDescription, updatedTaskDescription);
//
//        if (taskResponse != null){
//            return ResponseEntity.ok(taskResponse);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
//    }

}

