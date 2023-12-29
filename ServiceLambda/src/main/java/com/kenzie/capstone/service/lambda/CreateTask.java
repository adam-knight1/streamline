package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaTaskListService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CreateTask implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger log = LogManager.getLogger(UpdateTaskList.class);
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new GsonBuilder().create();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        LambdaTaskListService lambdaTaskListService = serviceComponent.provideLambdaTaskListService();

        String userId = input.getPathParameters().get("userId");
        if (userId == null || userId.length() == 0){
            return response.withStatusCode(400)
                    .withBody("userId is invalid");
        }
        String requestBody = input.getBody();
        TaskRequest taskRequest = gson.fromJson(requestBody , TaskRequest.class);

        if (taskRequest.getTaskName() == null || taskRequest.getTaskName().isEmpty()){
            return response
                    .withStatusCode(400)
                    .withBody("Task name is required");
        }
        try {
            TaskResponse taskResponse = lambdaTaskListService.createTask(userId , taskRequest);
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(taskResponse));
        } catch (IllegalArgumentException e){
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e));
        }
    }
}
