package com.kenzie.capstone.service.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.kenzie.capstone.service.LambdaTaskService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.model.CompleteTaskRequest;
import com.kenzie.capstone.service.model.CompleteTaskResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Lambda function handler for marking a task as complete.
 */
public class CompleteTask implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger log = LogManager.getLogger();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Input: {}", gson.toJson(input));
        LambdaTaskService lambdaTaskService = DaggerServiceComponent.create().provideLambdaTaskService();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(Map.of("Content-Type", "application/json"));

        try {
            CompleteTaskRequest completeTaskRequest = gson.fromJson(input.getBody(), CompleteTaskRequest.class);
            CompleteTaskResponse completeTaskResponse = lambdaTaskService.completeTask(completeTaskRequest.getUserId(), completeTaskRequest.getTaskId());
            String output = gson.toJson(completeTaskResponse);
            return response.withStatusCode(200).withBody(output);
        } catch (Exception e) {
            log.error("Error in Complete Task Lambda: ", e);
            return response.withStatusCode(500).withBody(gson.toJson(Map.of("error", e.getMessage())));
        }
    }
}

