package com.wgapp.worksheetgenerator.Database;

public class UserEntity {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int pinNumber;

    public UserEntity(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public UserEntity() {

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

    public void setUserId(int id) {
        this.id = id;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }
}

