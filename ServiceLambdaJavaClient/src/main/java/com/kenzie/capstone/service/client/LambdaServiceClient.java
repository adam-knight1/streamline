package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.*;


public class LambdaServiceClient {
    private static final String GET_EXAMPLE_ENDPOINT = "example/{id}";
    private static final String SET_EXAMPLE_ENDPOINT = "example";
    private static final String CREATE_USER_ENDPOINT = "/user/create";
    private static final String CREATE_TASKLIST_ENDPOINT = "/taskList/create";
    private static final String UPDATE_TASKLIST_ENDPOINT = "/taskList/update";

    private ObjectMapper mapper;

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

    public TaskListResponse updateTaskList(TaskListRequest taskListRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskListRequest);
        String response = endpointUtility.postEndpoint("taskList/update", requestData);
        TaskListResponse taskListResponse;
        try {
            taskListResponse = mapper.readValue(response, TaskListResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return taskListResponse;
    }

    public TaskResponseLambda addTaskToTaskList (String userId, String taskListName, TaskRecord taskRecord)throws JsonProcessingException{
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskRecord);
        String response = endpointUtility.postEndpoint("task/add", requestData);
        TaskResponseLambda taskResponseLambda;
        try {
            taskResponseLambda = mapper.readValue(response, TaskResponseLambda.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON:" + e);
        }
        return taskResponseLambda;

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


    public boolean updateTask(String taskId, String taskName, String taskDescription) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();

        TaskRequest taskRequest = new TaskRequest(taskId,taskName,taskDescription);

        try {
            String requestData = mapper.writeValueAsString(taskRequest);
            String endpoint = "task/update/" + taskId;

            String response = endpointUtility.postEndpoint(endpoint, requestData);
            TaskResponseLambda taskResponseLambda = mapper.readValue(response, TaskResponseLambda.class);
            return taskResponseLambda.isCompleted();
        }catch (JsonProcessingException e){
            throw e;
        }catch (Exception e){
            throw new ApiGatewayException("Error updating task: " + e.getMessage());
        }
    }
    //was taskrecord update to taskresponselambda

    public TaskResponseLambda createTask(TaskRequest taskRequest)throws JsonProcessingException{
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskRequest);
        String response = endpointUtility.postEndpoint("task/create", requestData);

        TaskResponseLambda taskResponse;

        try{
            taskResponse= mapper.readValue(response, TaskResponseLambda.class);
        } catch (Exception e ) {
            throw new ApiGatewayException("unable to map deserialize JSON: " +e);
        }
        return taskResponse;
    }
}


