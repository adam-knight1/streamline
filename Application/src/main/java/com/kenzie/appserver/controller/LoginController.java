package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    //slated for deletion pending move of spring security components
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(UserService userService) {
        this.userService = userService;
    }

}
