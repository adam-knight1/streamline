package com.kenzie.appserver;

import com.kenzie.appserver.controller.model.TaskResponse;

public class TaskCreationException extends RuntimeException{
    private TaskResponse taskResponse;

    public TaskCreationException(TaskResponse taskResponse, String message){
        super(message);
        this.taskResponse = taskResponse;
    }
    public TaskResponse getTaskResponse(){
        return taskResponse;
    }
}
