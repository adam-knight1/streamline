package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.controller.model.TaskListResponse;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.TaskListService;
import com.kenzie.capstone.service.model.GetTaskListLambdaResponse;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@IntegrationTest
class TaskListControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    TaskListService taskListService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createTaskList_CreateSuccessful() throws Exception {
        String userId = "json102";
        String taskListName = "Task List";
        List<TaskRecord> tasks = Collections.emptyList();
        TaskListResponse taskListResponse = new TaskListResponse(userId, taskListName, tasks);
        TaskListCreateRequest createRequest = new TaskListCreateRequest();
        createRequest.setUserId(userId);
        createRequest.setTaskListName(taskListName);

        //when(taskListService.createTaskList()).thenReturn(taskListResponse);

        mvc.perform(post("/taskList/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("json102"))
                .andExpect(jsonPath("taskListName").value("Task List"));
    }
    @Test
    public void testGetTaskListByUserId () throws Exception {
        String userId = "json102";
        String taskListName = "Task List";
        List<TaskRecord> tasks = Collections.emptyList();

        TaskListCreateRequest createRequest = new TaskListCreateRequest();
        createRequest.setUserId(userId);
        createRequest.setTaskListName(taskListName);


        mvc.perform(get("/taskList/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("json102"))
                .andExpect(jsonPath("taskListName").value("Task List"));

    }
}