package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.TaskService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.TaskRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UpdateTask implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
   // private final TaskService taskService;

  //  public UpdateTask(){
  //      ServiceComponent serviceComponent = DaggerServiceComponent.create();
  //      this.taskService = serviceComponent.provideTaskService();
  //  }

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
       // JsonStringToReferralConverter jsonStringToReferralConverter = new JsonStringToReferralConverter();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        // Logging the request json to make debugging easier.
        log.info(gson.toJson(input));


        //why is this still throwing error?
        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        TaskService taskService = serviceComponent.provideTaskService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String id = input.getPathParameters().get("id");

        if (id == null || id.length() == 0) {
            return response
                    .withStatusCode(400)
                    .withBody("taskId is invalid");
        }

        // Get the JSON payload for updating the task
        String requestBody = input.getBody();
        TaskRequest taskRequest = gson.fromJson(requestBody, TaskRequest.class);

        try {
            // Perform the task update using TaskService
            TaskService updatedTask = taskService.updateTask(id, taskRequest);

            // Construct the response body with the updated task details
            String responseBody = gson.toJson(updatedTask);

            return response
                    .withStatusCode(200)
                    .withBody(responseBody);
        } catch (Exception e) {
            log.error("Error updating task: " + e.getMessage());
            return response
                    .withStatusCode(500)
                    .withBody("Error updating task");
        }
    }
}
