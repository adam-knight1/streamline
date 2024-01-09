package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.TaskRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@EnableScan
public interface TaskRepository extends CrudRepository<TaskRecord, String> {

    //find tasks by userId
    List<TaskRecord> findByUserId (String userId);

    //delete tasks by userid
    void deleteByUserId(String userId);


   // Optional<TaskRecord> findById(String taskId);
}