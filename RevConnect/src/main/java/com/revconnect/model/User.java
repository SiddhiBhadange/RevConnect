package com.revconnect.model;

import java.sql.Timestamp;

public class User {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    private int id;
    private String email;
    private String password;
    private String userType;


    public User(String username, String email, String password, String userType) {
        this.username = username;
        this.email = email;
        this.password  = password;
        this.userType = userType;
    }

    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    // Constructors
    public User() {
    }


}
