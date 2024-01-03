package com.kenzie.appserver;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DynamoDBUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserRecord> optionalUserRecord = userRepository.findByUsername(username);

        if (optionalUserRecord.isPresent()) {
            UserRecord userRecord = optionalUserRecord.get();

            return new CustomUserDetails(userRecord);
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Optional<UserRecord> optionalUserRecord = userRepository.findByUserId(userId);

        if (optionalUserRecord.isPresent()) {
            UserRecord userRecord = optionalUserRecord.get();
            return new CustomUserDetails(userRecord);
        } else {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
    }
}

