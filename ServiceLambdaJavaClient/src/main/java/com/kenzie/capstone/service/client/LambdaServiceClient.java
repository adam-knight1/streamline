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

/**
 * This LambdaServiceClient class is responsible for communicating with various AWS Lambda functions.
 * It abstracts the details of sending requests and processing responses to and from the Lambda services.
 * This client is used to create users, add tasks, retrieve task lists, and perform other actions
 * through the exposed Lambda functions. It uses the AWS SDK's ObjectMapper to serialize and deserialize
 * request and response objects to JSON. The client includes robust logging to facilitate tracking
 * of requests and responses for debugging and auditing purposes.
 */
public class LambdaServiceClient {
    private static final String COMPLETE_TASK_ENDPOINT = "/task/complete";
    private ObjectMapper mapper;
    private static final Logger log = LogManager.getLogger(LambdaServiceClient.class);

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Sends a request to create a new user and processes the response.
     * Serializes the user request object into JSON and posts it to the user creation endpoint.
     *
     * @param userRequest The user request object containing new user information.
     * @return UserResponseLambda object with user creation details.
     * @throws JsonProcessingException If JSON serialization or deserialization fails.
     */
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

    /**
     * Sends a request to add a new task to a task list and processes the response.
     * Serializes the task add request object into JSON and posts it to the task addition endpoint.
     *
     * @param taskAddRequest The task add request object containing task details.
     * @return TaskAddResponse object with task addition details.
     * @throws JsonProcessingException If JSON serialization or deserialization fails.
     */
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

    /**
     * Finds a user by their user ID.
     * Sends a get request to the user retrieval endpoint and processes the response.
     *
     * @param userId The unique identifier of the user to find.
     * @return UserResponseLambda object with the user's details.
     * @throws JsonProcessingException If JSON deserialization fails.
     */
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

    /**
     * Finds a task list by the user ID.
     * Sends a get request to the task list retrieval endpoint and processes the response.
     *
     * @param userId The unique identifier of the user whose task list is to be retrieved.
     * @return GetTaskListLambdaResponse object with the task list details.
     * @throws JsonProcessingException If JSON deserialization fails.
     */

    //todo-slated for deletion pending test, also TasklistService potentially
    /*public GetTaskListLambdaResponse findTaskListByUserId(String userId) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint("taskList/" + userId);

        GetTaskListLambdaResponse getTaskListLambdaResponse;
        try {
            getTaskListLambdaResponse = mapper.readValue(response, GetTaskListLambdaResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return getTaskListLambdaResponse;
    }*/

    /**
     * Finds a user by their username.
     * Sends a get request to the user retrieval endpoint by username and processes the response.
     *
     * @param username The username of the user to find.
     * @return UserResponseLambda object with the user's details.
     * @throws JsonProcessingException If JSON deserialization fails.
     */
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

    /**
     * Retrieves all tasks associated with a given user ID.
     * Sends a get request to the tasks retrieval endpoint and processes the response.
     *
     * @param userId The unique identifier of the user whose tasks are to be retrieved.
     * @return A list of TaskRecord objects for the specified user.
     * @throws JsonProcessingException If JSON deserialization fails.
     */
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

    /**
     * Marks a task as complete.
     * Builds the request payload and sends a post request to the task completion endpoint.
     *
     * @param userId The unique identifier of the user who owns the task.
     * @param taskId The unique identifier of the task to be marked as complete.
     * @param status The new status of the task.
     * @throws JsonProcessingException If JSON serialization fails.
     */
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

        } catch (Exception e) {
            // request error log
            log.error("Error calling endpoint to complete task for userId: {} and taskId: {}, status: {}", userId, taskId, status, e);
            throw new ApiGatewayException("Error completing task with userId: " + userId + " and taskId: " + taskId + ", status: " + status, e);
        }
    }
}





