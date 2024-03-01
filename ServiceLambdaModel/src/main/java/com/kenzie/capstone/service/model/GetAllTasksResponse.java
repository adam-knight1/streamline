package com.kenzie.capstone.service.model;

import java.util.List;

/**
 * GetAllTasksResponse is a DTO that encapsulates a collection of TaskResponse objects.
 * It is used to return a list of tasks typically in response to a query for all tasks
 * associated with a particular user.
 */
public class GetAllTasksResponse {
    private List<TaskResponse> tasks;

    public List<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }
}



