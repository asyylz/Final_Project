package com.wgapp.worksheetgenerator.Services.Impl;

import com.wgapp.worksheetgenerator.DAO.Entities.UserEntity;
import com.wgapp.worksheetgenerator.DAO.Impl.UserDAOImpl;
import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.Services.UserService;
import com.wgapp.worksheetgenerator.Utils.Utils;
import javafx.scene.control.Alert;

public class UserServiceImpl implements UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    public UserServiceImpl(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public UserServiceImpl() {
        this.userDAO = userDAO;
    }


    @Override
    public void register(UserEntity userEntity) {

        // Validate the password before proceeding
        String password = userEntity.getPassword();
        String validationMessage = getPasswordValidationMessage(password);
        if (validationMessage != null) {
            Utils.notifyUser(validationMessage, "Invalid Password", "Password Error", Alert.AlertType.ERROR);
            throw new IllegalArgumentException(validationMessage);
        }
        // Create the user using the UserDAO and get the created UserEntity
        userDAO.createUser(userEntity.getUsername(), password);
    }

    @Override
    public UserEntity login(UserEntity userEntity) {
        return userDAO.findUserByUsername(userEntity.getUsername(), userEntity.getPassword());

    }

    // service
    @Override
    public void setPinNumber(UserEntity userEntity) {
        userDAO.setPinNumber(userEntity.getPinNumber(), userEntity.getUsername());
    }

    @Override
    public void updatePassword(UserEntity userEntity) {
        String oldPassword = userEntity.getPassword();
        String newPassword = userEntity.getNewPassword();
        String validationMessage = getPasswordValidationMessage(newPassword);
        if (validationMessage == null) {
            userDAO.updatePassword(userEntity.getUsername(), oldPassword, newPassword);
        }

    }


    private String getPasswordValidationMessage(String password) {
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter.";
        }
        return null; // No errors
    }
}
