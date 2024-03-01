package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaTaskService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Lambda function handler for adding a task to a task list.
 */
public class AddTaskToTaskList implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger log = LogManager.getLogger();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        LambdaTaskService lambdaTaskService = serviceComponent.provideLambdaTaskService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            TaskAddRequest taskAddRequest = gson.fromJson(input.getBody(), TaskAddRequest.class);

            TaskRecord taskRecord = new TaskRecord();
            taskRecord.setTitle(taskAddRequest.getTitle());
            taskRecord.setBody(taskAddRequest.getBody());
            taskRecord.setUserId(taskAddRequest.getUserId());
            taskRecord.setStatus("incomplete");
            taskRecord.setTaskId(UUID.randomUUID().toString());

           TaskAddResponse taskAddResponse = lambdaTaskService.taskAddToTaskList(taskRecord);

           String output = gson.toJson(taskAddResponse);

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            log.error("Error in Add Task To Task List Lambda: ", e);
            return response
                    .withStatusCode(500)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
