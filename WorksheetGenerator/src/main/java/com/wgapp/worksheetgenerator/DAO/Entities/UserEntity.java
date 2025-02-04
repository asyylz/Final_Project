package com.wgapp.worksheetgenerator.DAO.Entities;

public class UserEntity {

    private int id;
    private String username;
    private String password;
    private int pinNumber;
private  String newPassword;

    public UserEntity() {

    }

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserEntity(int id, String username, String password, int pinNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.pinNumber = pinNumber;
    }

    public UserEntity(int id, String username, String password, String newPassword) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public void setUserId(int userId) {
        this.id = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

