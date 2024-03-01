package com.kenzie.capstone.service.model;

Copy code
/**
 * UserRequest is a DTO that encapsulates user credentials and details for user-related operations.
 * It includes the unique identifier, email, password, and username for the user. This class is
 * typically used when creating a new user or updating existing user details.
 */
public class UserRequest {
    private String userId;
    private String email;
    private String password;
    private String username;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
