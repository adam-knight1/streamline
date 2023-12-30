 package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.model.TaskList;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.config.Task;

import java.util.UUID;

@EnableScan
public interface TaskListRepository extends CrudRepository<TaskListRecord, String> {
    TaskList findById(UUID id);

    String findNameById(UUID id);

    TaskList save(TaskList taskList);

    TaskList updateListName(String id);

    void deleteById(UUID id);
}
