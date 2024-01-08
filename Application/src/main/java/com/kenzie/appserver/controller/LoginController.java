package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.UserLoginRequest;
import com.kenzie.appserver.controller.model.UserLoginResponse;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    public LoginController(UserService userService) {
        this.userService = userService;
    }

  /*  @GetMapping("/login.html")
    public String showLoginPage() {
        return "login";
    }
*/

   /* @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            logger.error("Invalid login request: Missing username or password");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        String username = loginRequest.getUsername().trim();
        String password = loginRequest.getPassword().trim();

        if (username.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password must not be empty");
        }

        try {
            Optional<UserRecord> authenticatedUserOpt = userService.authenticateUser(username, password);
            if (authenticatedUserOpt.isPresent()) {
                UserRecord authenticatedUser = authenticatedUserOpt.get();
                return ResponseEntity.ok(new UserLoginResponse(authenticatedUser.getUserId(), authenticatedUser.getUsername()));
            } else {
                logger.warn("Authentication failed for user: {}", username);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            logger.error("Login error: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
