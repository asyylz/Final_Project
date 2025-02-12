package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.*;

public class UserProperty {
    private StringProperty username = new SimpleStringProperty("");
    private StringProperty password = new SimpleStringProperty("");
    private IntegerProperty pin = new SimpleIntegerProperty(0);
    private StringProperty newPassword = new SimpleStringProperty("");
    private StringProperty oldPassword = new SimpleStringProperty("");
    private IntegerProperty userId = new SimpleIntegerProperty(0);


    public UserProperty() {
    }

    public UserProperty(String username) {
        this.username.set(username);
    }

    public UserProperty(int userId, String username, int pin) {
        this.userId.set(userId);
        this.username.set(username);
        this.pin.set(pin);
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

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getPin() {
        return pin.get();
    }

    public void setPin(int pin) {
        this.pin.set(pin);
    }

    public String getNewPassword() {
        return newPassword.get();
    }

    public void setNewPassword(String newPassword) {
        this.newPassword.set(newPassword);
    }

    public int getUserId() {
        return userId.get();
    }


}
