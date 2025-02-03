package com.wgapp.worksheetgenerator.DAO;

import com.wgapp.worksheetgenerator.DAO.Entities.UserEntity;

public interface UserDAO {
    UserEntity createUser(String userName, String password);
    UserEntity findUserByUsername(String username, String password);
    void setPinNumber(int pinNumber, String username);
    void updatePassword(String oldPassword, String newPassword, String username);
}
