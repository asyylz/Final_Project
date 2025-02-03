package com.wgapp.worksheetgenerator.DTOs;

public class UserDTO {
    private String username;
    private String password;
    private int pinNumber;
    private String newPassword;
    private String oldPassword;


    public UserDTO(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UserDTO(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public UserDTO() {

    }

    public UserDTO(String username, int pinNumber) {
        this.pinNumber = pinNumber;
        this.username = username;
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


    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
