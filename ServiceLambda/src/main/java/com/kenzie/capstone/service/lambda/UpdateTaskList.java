package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaTaskListService;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;

import java.util.HashMap;
import java.util.Map;

import com.kenzie.capstone.service.model.TaskListRequest;
import com.kenzie.capstone.service.model.TaskListResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateTaskList implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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

        String userId = null;

        // Check if path parameters are present and not null
        Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters != null) {
            userId = input.getPathParameters().get("userId");
        }

        if (userId == null || userId.length() == 0) {
            return response
                    .withStatusCode(400)
                    .withBody("userId is null");
        }

        // Get the JSON payload for updating the taskList
        String requestBody = input.getBody();
        TaskListRequest taskListRequest = gson.fromJson(requestBody, TaskListRequest.class);

        try {
            // Perform the taskList update using LambdaTaskListService
            TaskListResponse taskListResponse = lambdaTaskListService.updateTaskList(userId, taskListRequest);
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(taskListResponse));
        } catch (IllegalArgumentException e) {
            log.error("Error in UpdateTaskList Lambda: ", e);
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
