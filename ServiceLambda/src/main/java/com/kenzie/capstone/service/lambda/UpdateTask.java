package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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

public class UpdateTask implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger log = LogManager.getLogger(UpdateTask.class);
   // static final Logger log = LogManager.getLogger();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
       // JsonStringToReferralConverter jsonStringToReferralConverter = new JsonStringToReferralConverter();
       // GsonBuilder builder = new GsonBuilder();
       // Gson gson = builder.create();


        log.info(gson.toJson(input));


        //why is this still throwing error?
        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        LambdaTaskService lambdaTaskService = serviceComponent.provideLambdaTaskService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            TaskRequest taskRequest = gson.fromJson(input.getBody(),TaskRequest.class);

            TaskRecord taskRecord = new TaskRecord();
            taskRecord.setTaskDescription(taskRequest.getTaskDescription());
            taskRecord.setTaskName(taskRequest.getTaskName());
            taskRecord.setTaskId(taskRequest.getTaskId());
            taskRecord.setUserId(taskRequest.getUserId());
            taskRecord.setCompleted(taskRequest.isCompleted());

            TaskResponseLambda taskResponseLambda = lambdaTaskService.updateTask(
                    taskRecord.getTaskId(),
                    taskRecord.getTaskName(),
                    taskRecord.getTaskDescription(),
                    taskRecord.isCompleted());
            String output = gson.toJson(taskResponseLambda);

            return response
                    .withStatusCode(200)
                    .withBody(output);
        }catch (Exception exception){
            log.error("Error in UpdateTask Lambda: ",exception);
            return response
                    .withStatusCode(500)
                    .withBody(gson.toJson(exception.getMessage()));
        }
        }
//
//        String id = input.getPathParameters().get("id");
//
//        if (id == null || id.length() == 0) {
//            log.error("Invalid taskId");
//            return response
//                    .withStatusCode(400)
//                    .withBody("taskId is invalid");
//        }
//
//        String requestBody = input.getBody();
//        TaskRequest taskRequest;
//
//        try {
//
//            taskRequest = gson.fromJson(requestBody, TaskRequest.class);
//        } catch (JsonSyntaxException e ) {
//            log.error("Invalid JSON Syntax in request body:" + e.getMessage());
//            return response
//                    .withStatusCode(200)
//                    .withBody("Invalid JSON Syntax in request body");
//        }
//        try {
//
//
//            TaskResponseLambda updatedTask = lambdaTaskService.updateTask(taskRequest.getTaskId(), taskRequest.getTaskName(),
//                    taskRequest.getTaskDescription());
//
//            String responseBody = gson.toJson(updatedTask);
//            return response
//                    .withStatusCode(200)
//                    .withBody(responseBody);
//        }catch (IllegalArgumentException e ){
//            log.error("Error updating task: " + e.getMessage());
//            return response
//                    .withStatusCode(400)
//                    .withBody(gson.toJson(e.getMessage()));
//
//        } catch (Exception e) {
//            log.error("Error updating task:" +e.getMessage());
//            return response
//                    .withStatusCode(500)
//                    .withBody("Error updating task");
//        }
//    }
}
