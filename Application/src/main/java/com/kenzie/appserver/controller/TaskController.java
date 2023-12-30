
package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.TaskRecord;
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
    public List<TaskRecord> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add")
    public ResponseEntity<TaskRecord> addTask(@RequestBody TaskRecord task){
        TaskRecord addedTask = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTask);
    }

    //update task status by taskId
    @PostMapping("/{taskId}/update-status")
    public ResponseEntity<TaskRecord> updateTaskStatus(@PathVariable String taskId, @RequestParam boolean newStatus) {
        TaskRecord updatedTask = taskService.updateTaskStatus(taskId, newStatus);
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
}
