package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.*;


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

    public TaskResponse addTaskToTaskList (String userId, String taskListName,TaskRequest taskRequest)throws JsonProcessingException{
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(taskRequest);
        String response = endpointUtility.postEndpoint("task/add", requestData);
        TaskResponse taskResponse;
        //deserializing response into a TaskResponse object
        try {
            taskResponse = mapper.readValue(response, TaskResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON:" + e);
        }
        return taskResponse;

    }
    public TaskResponse updateTask (String taskId, TaskRequest updatedTaskRequest) throws JsonProcessingException {
        EndpointUtility endpointUtility = new EndpointUtility();
        String requestData = mapper.writeValueAsString(updatedTaskRequest);
        String endpoint = "task/update/" + taskId;

        String response = endpointUtility.postEndpoint(endpoint,requestData);

        TaskResponse taskResponse;
        try {
            taskResponse = mapper.readValue(response,TaskResponse.class);
        }catch (Exception e){
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return taskResponse;
    }

}
