package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponse;


public class LambdaServiceClient {
    private static final String GET_EXAMPLE_ENDPOINT = "example/{id}";
    private static final String SET_EXAMPLE_ENDPOINT = "example";
    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public UserResponse createUser(UserRequest userRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(userRequest);
        //write value as string since I'm using userRequest object rather than data string like example"
        String response = endpointUtility.postEndpoint("user/create", requestData);
        UserResponse userResponse;
        try {
            userResponse = mapper.readValue(response, UserResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return userResponse;
    }

    public TaskResponse addTaskToTaskList (String userId, String taskListName,TaskRequest taskRequest)throws JsonProcessingException{
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskRequest);
        String response = endpointUtility.postEndpoint("task/add", requestData);

        //deserializing response into a TaskResponse object
        TaskResponse taskResponse = mapper.readValue(response, TaskResponse.class);

        return taskResponse;

    }

}
