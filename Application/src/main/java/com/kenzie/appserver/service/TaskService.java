
package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenzie.appserver.TaskCreationException;
import com.kenzie.appserver.controller.model.TaskCreateRequest;
import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.repositories.TaskListRepository;
import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import com.kenzie.appserver.service.model.TaskList;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponseLambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private LambdaServiceClient lambdaServiceClient = new LambdaServiceClient();

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository,LambdaServiceClient lambdaServiceClient) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public List<TaskRecord> getAllTasks() {
        return (List<TaskRecord>) taskRepository.findAll();
    }

    public TaskRecord addTask (TaskRecord task) {
        return taskRepository.save(task);
    }

    public TaskResponse createTask(TaskRequest taskRequest){
        TaskResponse taskResponse = new TaskResponse();

        try {
            boolean updateSuccessful = lambdaServiceClient.createTask(taskRequest);
            if (updateSuccessful) {
                taskResponse.setTaskId(taskRequest.getTaskId());
                taskResponse.setTaskName(taskRequest.getTaskName());
                taskResponse.setTaskDescription(taskRequest.getTaskDescription());
                taskResponse.setMessage("Task created Successfully");
            } else {
                taskResponse.setMessage("Failed to create task");
            }
        } catch (Exception e){
            taskResponse.setMessage("Error creating task:" +e.getMessage());

        }
        return taskResponse;
    }

    public TaskRecord addTaskToTaskList(String taskListId, TaskRecord task) {
       //checking if task list exists
        TaskListRecord taskListRecord = taskListRepository.findById(taskListId).orElse(null);
        if (taskListRecord != null) {
            taskListRecord.addTask(task);//set tasklist Id on task
            taskListRepository.save(taskListRecord); //save updatedTask
            return task;
        }
        return null;
    }

    public TaskRecord updateTaskStatus(String taskId, boolean newStatus){
        TaskRecord task = taskRepository.findById(taskId).orElse(null);
        if(task != null){
            task.setCompleted(newStatus);
            return taskRepository.save(task);
        }
        return null;
    }


    public boolean deleteTask(String taskId) {
        if(taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }

    public TaskResponse updateTask (String taskId, String taskName, String taskDescription) {
        TaskResponse taskResponse = new TaskResponse();

        try{
            boolean updateSuccessful = lambdaServiceClient.updateTask(taskId,taskName,taskDescription);
            if (updateSuccessful){
                taskResponse.setTaskId(taskId);
                taskResponse.setTaskName(taskName);
                taskResponse.setTaskDescription(taskDescription);
                taskResponse.setMessage("Task updated successfully");
            }else{
                taskResponse.setMessage("Failed to update task details");
            }
        }catch (Exception exception){
            taskResponse.setMessage("Error updating task: " + exception.getMessage());
        }
        return taskResponse;
    }
}
