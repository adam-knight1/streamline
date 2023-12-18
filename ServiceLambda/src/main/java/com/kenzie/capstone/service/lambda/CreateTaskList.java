package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.TaskListService;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.TaskListRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateTaskList implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger log = LogManager.getLogger(CreateTaskList.class);

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new GsonBuilder().create();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);

        //Incorporated DaggerServiceComponent following AM, same error
        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        TaskListService taskListService = serviceComponent.provideTaskListService();

        String requestBody = input.getBody();

        //Deserialize JSON to TaskListRequest object
        TaskListRequest taskListRequest = gson.fromJson(requestBody, TaskListRequest.class);
        String userID = taskListRequest.getUserId();
        String taskListName = taskListRequest.getTaskListName();

        try{
            taskListService.createTaskList(userID, taskListName);
            return response.withStatusCode(200).withBody("Task list successfully created.");
        }catch(IllegalArgumentException e){
            log.error("Invalid argument: " + e.getMessage());
            return response.withStatusCode(400).withBody("Invalid argument.");
        }//More specific error handling might be helpful
        catch(Exception e){
            log.error("Error creating task list: " + e.getMessage());
            return response.withStatusCode(500).withBody("Error creating task list.");
        }
    }
}
