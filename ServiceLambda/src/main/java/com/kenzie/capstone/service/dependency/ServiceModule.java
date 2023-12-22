package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.LambdaTaskService;
import com.kenzie.capstone.service.LambdaUserService;
import com.kenzie.capstone.service.dao.ExampleDao;

import com.kenzie.capstone.service.dao.TaskDao;
import com.kenzie.capstone.service.dao.UserDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public LambdaService provideLambdaService(@Named("ExampleDao") ExampleDao exampleDao) {
        return new LambdaService(exampleDao);
    }
    @Singleton
    @Provides
    @Inject
    public LambdaTaskService provideLambdaTaskService(@Named("TaskDao") TaskDao taskDao) {
        return new LambdaTaskService(taskDao);
    }


    @Singleton
    @Provides
    @Inject
    public LambdaUserService provideUserService(@Named("UserDao") UserDao userDao) {
        return new LambdaUserService(userDao);
    }
}
