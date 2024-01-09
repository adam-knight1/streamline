package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import com.kenzie.appserver.service.model.TaskList;

import java.util.concurrent.TimeUnit;

public class CacheStore {
    private Cache<String, TaskList> cache;

    public CacheStore(int expiry, TimeUnit timeUnit) {
        // initalize the cache
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    //Method to fetch previously stored record using record key
    public TaskList get(String userId) {
        return cache.getIfPresent(userId);
    }

    //Method to put a new record in Cache Store with record key
    public void add(String userId, TaskList taskList) {
        if(userId != null && taskList != null) {
            cache.put(userId, taskList);
            System.out.println("Record stored in "
                    + taskList.getClass().getSimpleName()
                    + " Cache with Key = " + userId);
        }
    }
}

