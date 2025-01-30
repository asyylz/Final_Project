package com.wgapp.worksheetgenerator.DTOs;

public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int pinNumber;

    public UserDTO(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public UserDTO(String username, int pinNumber) {
        this.username = username;
        this.pinNumber = pinNumber;
    }

    public UserDTO() {

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
}
