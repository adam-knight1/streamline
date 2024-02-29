package com.kenzie.appserver.controller;

import com.kenzie.appserver.UserNotFoundException;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserLoginRequest;
import com.kenzie.appserver.controller.model.UserLoginResponse;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponseLambda;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseLambda> getUser(@PathVariable("userId") String userId) {
        System.out.println("Received request to find user with userId: " + userId);
        try {
            UserResponseLambda userResponseLambda = userService.findUserByUserId(userId);
            if (userResponseLambda == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(userResponseLambda);
        } catch (Exception e) {
            System.out.println("Error in fetching user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<UserResponseLambda> getUserByUsername(@PathVariable("username") String username){
        System.out.println("Received request to find user with username: " + username);
        //routing username back successfully 1/28
        try {
            UserResponseLambda userResponseLambda = userService.findUserByUsername(username);
            if (userResponseLambda == null) {
                System.out.println("Null value in user controller");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(userResponseLambda);
        } catch (Exception e) {
            System.out.println("Error in fetching user by username: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<UserResponseLambda> createNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(userCreateRequest.getUsername());
        userRequest.setPassword(userCreateRequest.getPassword());
        userRequest.setEmail(userCreateRequest.getEmail());
        if (userRequest.getUserId() == null) {
            userRequest.setUserId(UUID.randomUUID().toString());
        }

        try {
            UserResponse userResponse = userService.createNewUser(userRequest);

            UserResponseLambda userResponseLambda = new UserResponseLambda();
            userResponseLambda.setUserId(userRequest.getUserId());  //I changed this from userResponse.getUserId
            System.out.println("hi adam!" + userRequest.getUserId());  //this is different than what is in the table.
            userResponseLambda.setUsername(userResponse.getUsername());
            userResponseLambda.setEmail(userResponse.getEmail());

            return ResponseEntity.ok(userResponseLambda);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
