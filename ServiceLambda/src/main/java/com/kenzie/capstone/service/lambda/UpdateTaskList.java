package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.util.TaskListService;

import java.util.HashMap;
import java.util.logging.LogManager;

public class UpdateTaskList implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        TaskListService taskListService = serviceComponent.provideLambdaService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String id = input.getPathParameters().get("id");

        if (id == null || id.length() == 0) {
            return response
                    .withStatusCode(400)
                    .withBody("userId is invalid");
        }

        // unfinished -bs
        return response;
    }
}
