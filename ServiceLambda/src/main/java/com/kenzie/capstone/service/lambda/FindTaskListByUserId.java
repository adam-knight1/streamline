package com.kenzie.capstone.service.lambda;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaTaskListService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.TaskListRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class FindTaskListByUserId implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger log = LogManager.getLogger();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        LambdaTaskListService lambdaTaskListService = serviceComponent.provideLambdaTaskListService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        try {
            String userId = input.getPathParameters().get("userId");

            TaskListRecord foundTaskList = lambdaTaskListService.findTaskListByUserId(userId);
            if (foundTaskList == null) {
                return response
                        .withStatusCode(404)
                        .withBody("Task List not found");
            }

            String output = gson.toJson(foundTaskList);
            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            log.error("Error in FindTaskListByUserId Lambda: ", e);
            return response
                    .withStatusCode(500)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}

