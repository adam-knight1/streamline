package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.kenzie.appserver.IntegrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.kenzie.appserver.controller.model.TaskCreateRequest;
import com.kenzie.appserver.controller.model.TaskResponse;
import com.kenzie.appserver.service.TaskService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.testcontainers.shaded.okhttp3.MediaType;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.testcontainers.shaded.com.github.dockerjava.core.MediaType.APPLICATION_JSON;
import static org.testcontainers.shaded.okhttp3.MediaType.*;
import org.springframework.http.MediaType;


@IntegrationTest
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    TaskService taskService;

    private static final MockNeat mockNeat = MockNeat.threadLocal();

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup(){
    mapper.registerModule(new Jdk8Module());
    }

    @Test
    public void createTask_Success() throws Exception {
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest();
        taskCreateRequest.setTaskName("Sample Task");
        taskCreateRequest.setTaskDescription("Sample Task Description");
        taskCreateRequest.setTaskId("Sample Task ID");
       // taskCreateRequest.setCompleted(false);


        ResultActions result = (ResultActions) mvc.perform(MockMvcRequestBuilders.post("/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(taskCreateRequest)))
                        .andExpect(status().isOk())
                        .andReturn();
        //.accept(MediaType.APPLICATION_JSON)

        String responseBody = ((MvcResult) result).getResponse().getContentAsString();
        TaskResponse response = mapper.readValue(responseBody,TaskResponse.class);


        Assertions.assertEquals("Sample Task", response.getTaskName());
        Assertions.assertEquals("Sample Task ID", response.getTaskId());
        Assertions.assertEquals("Sample Task Description", response.getTaskDescription());
       // Assertions.assertFalse(response.isCompleted());



    }

}