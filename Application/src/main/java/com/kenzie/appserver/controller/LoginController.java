package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.UserLoginRequest;
import com.kenzie.appserver.controller.model.UserLoginResponse;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

  /*  @GetMapping("/login.html")
    public String showLoginPage() {
        return "login";
    }
*/

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        try {
            UserRecord authenticatedUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            if (authenticatedUser != null) {
                return ResponseEntity.ok(new UserLoginResponse(authenticatedUser.getUserId(), authenticatedUser.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials dude");
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
