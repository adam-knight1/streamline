/*
package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.TaskService;
import com.kenzie.appserver.service.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        Task addedTask = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTask);
    }

    @PostMapping("/{taskId}/update-status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable String taskId, @RequestParam String newStatus) {
        Task updatedTask = taskService.updateTaskStatus(taskId, newStatus);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<String > deleteTask (@PathVariable String taskId) {
        if(taskService.deleteTask(taskId)) {
            return ResponseEntity.ok("Task sucessfully deleted ");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}*/
