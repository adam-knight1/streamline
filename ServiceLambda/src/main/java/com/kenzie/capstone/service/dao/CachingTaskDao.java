package com.kenzie.capstone.service.dao;

import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kenzie.capstone.service.model.TaskRecord;

import java.util.List;

public class CachingTaskDao {

    private final LoadingCache<String, List<TaskRecord>> taskCache;

    public CachingTaskDao(TaskDao taskDao) {
        taskCache = CacheBuilder.newBuilder()
                .build(CacheLoader.from(taskDao::getTasksByUserId));
    }

    public List<TaskRecord> getTasks(String userId) {
        return taskCache.getUnchecked(userId);
    }
}



