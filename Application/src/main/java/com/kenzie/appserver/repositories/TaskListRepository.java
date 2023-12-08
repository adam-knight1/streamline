package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.TaskListRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface TaskListRepository extends CrudRepository<TaskListRecord, String> {
}
