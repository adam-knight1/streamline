package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class LambdaServiceClient {
    private static final String GET_EXAMPLE_ENDPOINT = "example/{id}";
    private static final String SET_EXAMPLE_ENDPOINT = "example";
    private static final String CREATE_USER_ENDPOINT = "/user/create";
    private static final String CREATE_TASKLIST_ENDPOINT = "/taskList/create";
    private static final String UPDATE_TASKLIST_ENDPOINT = "/taskList/update";
    private static final String CREATE_TASK_ENDPOINT = "/task/create";

    private ObjectMapper mapper;

    private static final Logger log = LogManager.getLogger(LambdaServiceClient.class);



    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public UserResponseLambda createUser(UserRequest userRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(userRequest);
        //write value as string since I'm using userRequest object rather than data string like example"
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
        String response = endpointUtility.postEndpoint("task/add",requestData);
        TaskAddResponse taskAddResponse;
        try {
            taskAddResponse = mapper.readValue(response,TaskAddResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Exception thrown at in lambda service client", e);
        }
        return taskAddResponse;
    }

    public TaskListResponse createTaskList(TaskListRequest taskListRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskListRequest);
        String response = endpointUtility.postEndpoint("taskList/create", requestData);
        TaskListResponse taskListResponse;
        try {
            taskListResponse = mapper.readValue(response, TaskListResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return taskListResponse;
    }


    public TaskResponseLambda addTask(String userId, String taskListName, TaskRecord taskRecord) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskRecord);
        String response = endpointUtility.postEndpoint(CREATE_TASK_ENDPOINT, requestData);
        TaskResponseLambda taskResponseLambda;
        try {
            taskResponseLambda = mapper.readValue(response, TaskResponseLambda.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON:" + e);
        }
        return taskResponseLambda;

    }


    public TaskResponseLambda updateTask( TaskRequest updatedTaskRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(updatedTaskRequest);
        String endpoint = "task/update/";

        String response = endpointUtility.postEndpoint(endpoint, requestData);

        TaskResponseLambda taskResponse;
        try {
            taskResponse = mapper.readValue(response, TaskResponseLambda.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return taskResponse;
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
        log.info("Attempting to find user by username: " + username);

        String response;
        try {
            response = endpointUtility.getEndpoint("user/name/" + username);
            log.info("Received response from endpoint: " + response); //
        } catch (Exception e) {
            log.error("Error calling endpoint: " + e.getMessage(), e);
            throw e;
        }

        UserResponseLambda userResponse;
        try {
            userResponse = mapper.readValue(response, UserResponseLambda.class);
            log.info("Successfully mapped response to UserResponseLambda");
        } catch (Exception e) {
            log.error("Unable to map deserialize JSON: " + e.getMessage(), e);
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e, e);
        }
        return userResponse;
    }


    public boolean updateTask(String taskName, String taskDescription) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();

        TaskRequest taskRequest = new TaskRequest( taskName, taskDescription);

        try {
            String requestData = mapper.writeValueAsString(taskRequest);
            String endpoint = "task/update/";

            String response = endpointUtility.postEndpoint(endpoint, requestData);
            TaskResponseLambda taskResponseLambda = mapper.readValue(response, TaskResponseLambda.class);
            return taskResponseLambda.isCompleted();
        } catch (JsonProcessingException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiGatewayException("Error updating task: " + e.getMessage());
        }

    }

}


