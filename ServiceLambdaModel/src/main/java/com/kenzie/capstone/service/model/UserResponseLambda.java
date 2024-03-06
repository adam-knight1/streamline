package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * UserResponseLambda is a DTO that encapsulates the response data for user-related operations.
 * It is used to transfer user data, including identification and authentication details, between
 * different layers of the application, mainly services and controllers, especially after user
 * creation or retrieval operations.
 */
@JsonIgnoreProperties(ignoreUnknown = true) //added to adjust duplicate username 200 response in
//find by username lambda function.
public class UserResponseLambda {
    private String userId;
    private String username;
    private String email;
    private String password;
    private Boolean userExists; // adding for duplicate username tests

    public Boolean getUserExists() {
        return userExists;
    }

    public void setUserExists(Boolean userExists) {
        this.userExists = userExists;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {return password;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password){this.password = password; }
}
