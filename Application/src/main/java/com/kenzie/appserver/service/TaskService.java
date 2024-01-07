
package com.kenzie.appserver.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public TaskResponse updateTask (int taskId, String takName, String taskDescription) {
        TaskResponse taskResponse = new TaskResponse();

        try{
            boolean updateSuccessful = lambdaServiceClient.updateTask(taskId,takName,taskDescription);
            if (updateSuccessful){
                taskResponse.setTaskId(taskId);
                taskResponse.setTaskName(takName);
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
