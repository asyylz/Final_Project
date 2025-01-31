package com.wgapp.worksheetgenerator.Database;

public interface UserDAO {
    UserEntity createUser(String userName, String password);
    UserEntity findUserByUsername(String username, String password);
    void setPinNumber(int pinNumber, String username);
}
