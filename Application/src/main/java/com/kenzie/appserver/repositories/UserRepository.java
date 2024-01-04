package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.UserRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository<UserRecord, String> {
//possiblt remove enable scan - adam
    Optional<UserRecord> findByUserId(String userId);
    Optional<UserRecord> findByUsername(String username);
    List<String> findUsersByUserId(String userId);

}