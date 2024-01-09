package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.TaskListCreateRequest;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.TaskListService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class TaskListControllerTest {
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
        TaskListCreateRequest createRequest = new TaskListCreateRequest();
        createRequest.setUserId(userId);
        createRequest.setTaskListName(taskListName);

        mvc.perform(post("/taskList/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(userId))
                .andExpect(jsonPath("taskListName").value(taskListName));
    }
}