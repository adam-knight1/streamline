package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaTaskListService;
import com.kenzie.capstone.service.LambdaTaskService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponseLambda;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CreateTask implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger log = LogManager.getLogger();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Starting CreateTask Lambda function");

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        LambdaTaskService lambdaTaskService = serviceComponent.provideLambdaTaskService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

      try {
          log.info("Input:" + gson.toJson(input));

          String body = input.getBody();
          log.info("Request body:" + body);

          if (body == null){
              log.error("Request body is null.");
              return response
                      .withStatusCode(400)
                      .withBody("Request body is null");
          }

          TaskRequest taskRequest = gson.fromJson(input.getBody(), TaskRequest.class);
          log.info("TaskRequest:" + gson.toJson(taskRequest));

          if (taskRequest.getTaskName() == null || taskRequest.getTaskName().isEmpty()) {
              log.error("Task name is null or empty");
              return response
                      .withStatusCode(400)
                      .withBody("Task name is null or empty");
          }

          TaskRecord taskRecord = new TaskRecord();
          taskRecord.setTaskName(taskRequest.getTaskName());
          taskRecord.setTaskId(taskRequest.getTaskId());
          taskRecord.setTaskDescription(taskRequest.getTaskDescription());
          taskRecord.setUserId(taskRequest.getUserId());
          taskRecord.setCompleted(taskRequest.isCompleted());

          TaskResponseLambda taskResponseLambda = lambdaTaskService.createTask(taskRecord);
          log.info("TaskResponseLambda:" + gson.toJson(taskResponseLambda));

          String output = gson.toJson(taskResponseLambda);
          log.info("Lambda function completed successfully");

          return response
                  .withStatusCode(200)
                  .withBody(output);
      } catch (Exception e) {
          log.error("Error in CreateTask Lambda: ", e);
          return response
                  .withStatusCode(500)
                  .withBody(gson.toJson(e.getMessage()));
      }

//        String userId = input.getPathParameters().get("userId");
//        if (userId == null || userId.length() == 0){
//            return response.withStatusCode(400)
//                    .withBody("userId is invalid");
//        }
//        String requestBody = input.getBody();
//        TaskRequest taskRequest = gson.fromJson(requestBody , TaskRequest.class);
//
//        if (taskRequest.getTaskName() == null || taskRequest.getTaskName().isEmpty()){
//            return response
//                    .withStatusCode(400)
//                    .withBody("Task name is required");
//        }
//        try {
//            TaskResponseLambda taskResponseLambda = lambdaTaskListService.createTask(userId , taskRequest);
//            return response
//                    .withStatusCode(200)
//                    .withBody(gson.toJson(taskResponseLambda));
//        } catch (IllegalArgumentException e){
//            return response
//                    .withStatusCode(400)
//                    .withBody(gson.toJson(e));
//        }
    }
}
