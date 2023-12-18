package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.service.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    public Task addTask (Task task) {
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(String taskId, String newStatus){
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task != null){
            task.setTaskStatus(newStatus);
            taskRepository.save(task);
        }
        return task;
    }

    public boolean deleteTask(String taskId) {
        if(taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }
}