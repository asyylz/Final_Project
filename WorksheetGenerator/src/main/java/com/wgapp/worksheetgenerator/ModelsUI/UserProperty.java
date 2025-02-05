package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.*;

public class UserProperty {
    private StringProperty username = new SimpleStringProperty("");
    private StringProperty password = new SimpleStringProperty("");
    private IntegerProperty pinNumber = new SimpleIntegerProperty(0);
    private StringProperty newPassword = new SimpleStringProperty("");
    private StringProperty oldPassword = new SimpleStringProperty("");
    private IntegerProperty userId = new SimpleIntegerProperty(0);


    public UserProperty() {
    }

    public UserProperty(String username) {
        this.username.set(username);
    }

    public UserProperty(int userId, String username, int pinNumber) {
        this.userId.set(userId);
        this.username.set(username);
        this.pinNumber.set(pinNumber);
    }



    public UserProperty(StringProperty username, StringProperty password) {
        this.username = username;
        this.password = password;
    }

    public UserProperty(String newUser, String newPass, String newPass1) {
    }

    public UserProperty(String text, String text1) {
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getPinNumber() {
        return pinNumber.get();
    }

    public IntegerProperty pinNumberProperty() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber.set(pinNumber);
    }

    public String getNewPassword() {
        return newPassword.get();
    }

    public StringProperty newPasswordProperty() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword.set(newPassword);
    }

    public String getOldPassword() {
        return oldPassword.get();
    }

    public StringProperty oldPasswordProperty() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword.set(oldPassword);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }
}
