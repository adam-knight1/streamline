package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

    @DynamoDBTable(tableName = "User")
    public class UserRecord {
        private String userId;

        private String email;

        private String username;

        private String password;

        public UserRecord(){
        }

        @DynamoDBHashKey(attributeName = "userId")
        public String getUserId() { return userId; }

        public void setUserId(String userId) { this.userId = userId; }

        @DynamoDBIndexHashKey(attributeName = "username", globalSecondaryIndexName = "UsernameIndex")
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        @DynamoDBAttribute(attributeName = "password")
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        @DynamoDBAttribute(attributeName = "email")
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserRecord)) return false;
            UserRecord that = (UserRecord) o;
            return Objects.equals(getUserId(), that.getUserId()) &&
                    Objects.equals(getUsername(), that.getUsername()) &&
                    Objects.equals(getPassword(), that.getPassword());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getUserId(), getUsername(), getPassword());
        }
    }




