package com.kenzie.capstone.service.model;

public class UserResponseLambda {
    private String userId;
    private String username;
    private String email;
    private String password; //I am only adding this to overcome a deserialization error.  There will be no getter. -adam

    public String getUserId() {
        return userId;
    }

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
