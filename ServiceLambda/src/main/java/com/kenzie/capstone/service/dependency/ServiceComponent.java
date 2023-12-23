package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaTaskListService;
import com.kenzie.capstone.service.LambdaTaskService;
import com.kenzie.capstone.service.LambdaUserService;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = {DaoModule.class, ServiceModule.class})
public interface ServiceComponent {
    LambdaTaskService provideLambdaTaskService();
    LambdaTaskListService provideLambdaTaskListService();
    LambdaUserService provideLambdaUserService();
}
