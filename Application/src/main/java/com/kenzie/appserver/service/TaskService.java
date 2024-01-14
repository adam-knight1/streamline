
package com.kenzie.appserver.service;

//import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.controller.model.TaskAddResponseModel;
//import com.kenzie.appserver.repositories.TaskRepository;
import com.kenzie.appserver.repositories.TaskRepository;
        import com.kenzie.capstone.service.client.LambdaServiceClient;
//import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskAddRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
 public class TaskService {
    private TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private LambdaServiceClient lambdaServiceClient = new LambdaServiceClient();

    @Autowired
    public TaskService(LambdaServiceClient lambdaServiceClient, TaskRepository taskRepository) {
        this.lambdaServiceClient = lambdaServiceClient;
        this.taskRepository = taskRepository;
    }

    public TaskAddResponseModel addTask(TaskAddRequest taskAddRequest){

        try {
            lambdaServiceClient.addTaskToTaskList(taskAddRequest);
        } catch (Exception e) {
            System.out.println("failure in taskService");
        }

        //generate the task response populating it with taskAddRequest

        TaskAddResponseModel taskAddResponse = new TaskAddResponseModel();
        taskAddResponse.setBody(taskAddRequest.getBody());
        taskAddResponse.setTitle(taskAddRequest.getTitle());

        return taskAddResponse;
    }

}



