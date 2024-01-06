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

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseLambda> getUserByUsername(@PathVariable("username") String username){
        System.out.println("Received request to find user with username: " + username);
        try {
            UserResponseLambda userResponseLambda = userService.findUserByUsername(username);
            if (userResponseLambda == null) {
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
            userResponseLambda.setUserId(userResponse.getUserId());
            System.out.println("hi adam!" + userResponse.getUserId());
            userResponseLambda.setUsername(userResponse.getUsername());
            userResponseLambda.setEmail(userResponse.getEmail());

            return ResponseEntity.ok(userResponseLambda);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




   /* @PutMapping("/{userId}")
    public ResponseEntity<UserResponseLambda> updateUser(@PathVariable("userId") String userId, @RequestBody User updatedUserInfo) {
        Optional<User> optionalUpdatedUser = userService.updateUser(userId, updatedUserInfo);
        return optionalUpdatedUser.map(user -> ResponseEntity.ok(userToResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PutMapping("/{userId}")
//    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody User updatedUserInfo) {
//        Optional<User> optionalUpdatedUser = userService.updateUser(userId, updatedUserInfo);
//        return optionalUpdatedUser.map(user -> ResponseEntity.ok(userToResponse(user)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }




//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
//        if(userService.deleteUser(userId)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }



    private UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    */

}
