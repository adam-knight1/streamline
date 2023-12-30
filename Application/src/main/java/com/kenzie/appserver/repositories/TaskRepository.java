package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ExampleRecord;

import com.kenzie.appserver.repositories.model.TaskRecord;
import com.kenzie.appserver.service.model.Task;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface TaskRepository extends CrudRepository<TaskRecord, String> {

    //find tasks by userId
    List<TaskRecord> findUserById (String userId);

    //delete tasks by userid
    void deleteByUserId(String userId);

}
