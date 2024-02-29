package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaServiceClient {
    private static final String COMPLETE_TASK_ENDPOINT = "/task/complete";

    private ObjectMapper mapper;

    private static final Logger log = LogManager.getLogger(LambdaServiceClient.class);

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public UserResponseLambda createUser(UserRequest userRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(userRequest);
        //write value as string since I'm using userRequest object rather than data string like example
        String response = endpointUtility.postEndpoint("user/create", requestData);

        System.out.println("Response from /user/create: " + response);

        UserResponseLambda userResponseLambda;
        try {
            userResponseLambda = mapper.readValue(response, UserResponseLambda.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return userResponseLambda;
    }

    public TaskAddResponse addTaskToTaskList(TaskAddRequest taskAddRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskAddRequest);
        String response = endpointUtility.postEndpoint("task/add", requestData);
        TaskAddResponse taskAddResponse;
        try {
            taskAddResponse = mapper.readValue(response, TaskAddResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Exception thrown at in lambda service client", e);
        }
        return taskAddResponse;
    }

    public UserResponseLambda findUserByUserId(String userId) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint("user/" + userId);

        UserResponseLambda userResponse;
        try {
            userResponse = mapper.readValue(response, UserResponseLambda.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return userResponse;
    }

    public GetTaskListLambdaResponse findTaskListByUserId(String userId) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint("taskList/" + userId);

        GetTaskListLambdaResponse getTaskListLambdaResponse;
        try {
            getTaskListLambdaResponse = mapper.readValue(response, GetTaskListLambdaResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return getTaskListLambdaResponse;
    }

    public UserResponseLambda findUserByUsername(String username) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String endpointUrl = "user/name/" + username;
        log.info("Attempting to find user by username: {}", username);
        log.debug("Constructed endpoint URL: {}", endpointUrl);

        String response;
        try {
            response = endpointUtility.getEndpoint(endpointUrl);
            log.info("Received response from endpoint: {}", response);
        } catch (Exception e) {
            log.error("Error calling endpoint for username '{}': {}", username, e.getMessage(), e);
            throw e;
        }

        UserResponseLambda userResponse;
        try {
            userResponse = mapper.readValue(response, UserResponseLambda.class);
            log.info("Successfully mapped response to UserResponseLambda for username '{}'", username);
        } catch (Exception e) {
            log.error("Unable to map deserialize JSON for username '{}': {}", username, e.getMessage(), e);
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e, e);
        }
        log.debug("Returning userResponse object for username '{}'", username);
        return userResponse;
    }

    public List<TaskRecord> getTasksByUserId(String userId) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response;
        try {
            log.info("Attempting to retrieve tasks for userId: {}", userId);
            response = endpointUtility.getEndpoint("task/user/" + userId);
            log.info("Received response from endpoint for userId {}: {}", userId, response);
        } catch (Exception e) {
            log.error("Error when calling endpoint to get tasks for userId: {} - Error: {}", userId, e.getMessage(), e);
            throw new ApiGatewayException("Error calling endpoint for tasks by userId: " + userId, e);
        }

        List<TaskRecord> tasks;
        try {
            TypeReference<List<TaskRecord>> typeRef = new TypeReference<>() {
            };
            tasks = mapper.readValue(response, typeRef);
            log.info("Successfully deserialized tasks for userId {}: {}", userId, tasks);
        } catch (Exception e) {
            log.error("Error deserializing tasks for userId: {} - Error: {}", userId, e.getMessage(), e);
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return tasks;
    }

    public void completeTask(String userId, String taskId, String status) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();

        // building the request payload
        Map<String, String> requestData = new HashMap<>();
        requestData.put("userId", userId);
        requestData.put("taskId", taskId);
        requestData.put("status", status);

        // Converting map to json
        String jsonRequestData = mapper.writeValueAsString(requestData);

        // Log
        log.info("Sending request to complete task with userId: {} and taskId: {}, status: {}", userId, taskId, status);

        try {
            String response = endpointUtility.postEndpoint(COMPLETE_TASK_ENDPOINT, jsonRequestData);

            log.info("Received response from completing task: {}", response);

            //will add response here
        } catch (Exception e) {
            // request error log
            log.error("Error calling endpoint to complete task for userId: {} and taskId: {}, status: {}", userId, taskId, status, e);
            throw new ApiGatewayException("Error completing task with userId: " + userId + " and taskId: " + taskId + ", status: " + status, e);

        }
    }
}





