package com.kenzie.capstone.service.model;

import java.util.List;

public class GetAllTasksResponse {
    private List<TaskResponse> tasks;

    // Constructors, getters, and setters

    public List<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }
}



