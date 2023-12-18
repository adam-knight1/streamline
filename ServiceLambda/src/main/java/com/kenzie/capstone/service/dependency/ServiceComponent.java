package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;

import com.kenzie.capstone.service.LambdaUserService;
import com.kenzie.capstone.service.TaskListService;
import com.kenzie.capstone.service.TaskService;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = {DaoModule.class, ServiceModule.class})
public interface ServiceComponent {
    LambdaService provideLambdaService();
    TaskService provideTaskService();
    TaskListService provideTaskListService();
    LambdaUserService provideLambdaUserService();
}
