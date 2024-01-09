package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Task;

import java.util.concurrent.TimeUnit;

public class CacheStore {
    private Cache<String, Task> cache;

    public CacheStore(int expiry, TimeUnit timeUnit) {
        // initalize the cache
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public Task get(String userId) {
        // Write your code here
        // Retrieve and return the concert
        return new Task();
    }

    public void evict(String userId) {
        // Write your code here
        // Invalidate/evict the concert from cache
    }

    public void add(String userId, Task task) {
        // Write your code here
        // Add concert to cache
    }
}

