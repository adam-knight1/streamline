package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.TaskListRecord;
import com.kenzie.appserver.service.model.TaskList;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@EnableScan
public interface TaskListRepository extends CrudRepository<TaskListRecord, String> {
    //As much of the logic has migrated over to AWS Lambda, this repository will be evaluated for any further utility.

}

