package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import java.util.Objects;

    @DynamoDBTable(tableName = "User")
    public class UserRecord {
        @Id
        @DynamoDBHashKey(attributeName = "userId")
        private String userId;

        //the last project had an issue setting sort keys
        //if possible we can change it this time
        //or use a GSI to look up by email or something - Adam
        @DynamoDBAttribute(attributeName = "email")
        private String email;

        @DynamoDBAttribute(attributeName = "username")
        private String username;

        @DynamoDBAttribute(attributeName = "password")
        private String password;

        public String getUserId() { return userId; }

        public void setUserId(String userId) { this.userId = userId; }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof com.kenzie.appserver.repositories.model.UserRecord)) return false;
            com.kenzie.appserver.repositories.model.UserRecord that = (com.kenzie.appserver.repositories.model.UserRecord) o;
            return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getUserId(), getUsername(), getPassword());
        }
    }

