package com.kenzie.appserver.controller;

import com.kenzie.appserver.UserNotFoundException;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

   /* @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
        System.out.println("Received request to find user with userId: " + userId);
        User user = userService.findByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found"); //see custom exception -adam
        }
        return ResponseEntity.ok(userToResponse(user));
    } */


   @PostMapping("/create")
    public ResponseEntity<UserResponse> createNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(userCreateRequest.getUsername());
        userRequest.setPassword(userCreateRequest.getPassword());
        userRequest.setEmail(userCreateRequest.getEmail());

        try {
            UserResponse userResponse = userService.createNewUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


   /* @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody User updatedUserInfo) {
        Optional<User> optionalUpdatedUser = userService.updateUser(userId, updatedUserInfo);
        return optionalUpdatedUser.map(user -> ResponseEntity.ok(userToResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if(userService.deleteUser(userId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    private UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

}
