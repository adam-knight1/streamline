package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private LambdaServiceClient lambdaServiceClient;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
            MockitoAnnotations.initMocks(this);
        }

    @Test
    void CreateNewUserTest() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setPassword("password");
        userRequest.setEmail("test@testing123.com");

        UserResponseLambda mockResponse = new UserResponseLambda();
        mockResponse.setUserId("1234567");
        mockResponse.setUsername(userRequest.getUsername());
        mockResponse.setEmail(userRequest.getEmail());

        when(lambdaServiceClient.createUser(any(UserRequest.class))).thenReturn(mockResponse);

        UserResponse result = userService.createNewUser(userRequest);

        assertNotNull(result);
       // assertEquals("1234567", result.getUserId());
        assertEquals("testUser", result.getUsername());
        assertEquals("test@testing123.com", result.getEmail());

        verify(lambdaServiceClient, times(1)).createUser(any(UserRequest.class));
    }


}


