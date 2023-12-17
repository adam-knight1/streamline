package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.LambdaUserService;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.util.DynamoDbClientProvider;

public class CreateUser implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LambdaUserService lambdaUserService;

    public CreateUser() {
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        UserDao userDao = new UserDao(mapper);
        this.lambdaUserService = new LambdaUserService(userDao);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            UserRequest userRequest = objectMapper.readValue(input.getBody(),UserRequest.class);

            UserRecord userRecord = La
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
