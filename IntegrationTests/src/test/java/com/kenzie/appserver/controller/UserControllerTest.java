package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.UserRequestTest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.service.UserService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.kenzie.capstone.service.model.UserRequest;




import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@IntegrationTest
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    UserService userService;


    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();



    @Test
    public void getUserByUserId_Exists() throws Exception {
        String username = mockNeat.strings().valStr();
        String email = mockNeat.emails().valStr();
        String password = mockNeat.passwords().valStr();

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setEmail(email);
        // userRequest.setUserId(UUID.randomUUID().toString()); I may not need this as the ID is set in the lambda package. -adam

        UserResponse userResponse = userService.createNewUser(userRequest);
        String userId = userResponse.getUserId();

        mvc.perform(get("/user/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId")
                        .value(is(userId)))
                .andExpect(jsonPath("$.username")
                        .value(is(username)))
                .andExpect(status().is2xxSuccessful());
    }




}
