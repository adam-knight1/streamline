package com.kenzie.capstone.service.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kenzie.capstone.service.model.TaskListRecord;
import com.kenzie.capstone.service.model.TaskRecord;

public class CachingTaskListDao {

    private final LoadingCache<String, TaskListRecord> taskListCache;

    public CachingTaskListDao(TaskListDao taskListDao) {
        taskListCache = CacheBuilder.newBuilder()
                .build(CacheLoader.from(taskListDao::getTaskListByUserId));
    }

    public TaskListRecord getTaskList(String userId){
        return taskListCache.getUnchecked(userId);
    }
}



