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
    public UserDTO register(UserDTO userDTO) {

        // Validate the password before proceeding
        String password = userDTO.getPassword();
        String validationMessage = getPasswordValidationMessage(password);
        if (validationMessage != null) {
            Utils.notifyUser(validationMessage,"Invalid Password","Password Error", Alert.AlertType.ERROR);
            throw new IllegalArgumentException(validationMessage);
        }

        // Create the user using the UserDAO and get the created UserEntity
        UserEntity userEntity = userDAO.createUser(userDTO.getUsername(), password);

        // Convert the UserEntity to a UserDTO to return
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setUsername(userEntity.getUsername());
        newUserDTO.setPassword(userEntity.getPassword()); // You may not want to return password, but for now, I included it.

        // Return the newly created UserDTO
        return newUserDTO;
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        UserEntity userEntity = userDAO.findUserByUsername(userDTO.getUsername(), userDTO.getPassword());

        // Convert UserEntity to UserDTO (don't expose password)
        return new UserDTO(userEntity.getUsername(), userEntity.getPinNumber());
    }

    // service
    @Override
    public void setPinNumber(UserDTO userDTO) {
        userDAO.setPinNumber(userDTO.getPinNumber(), userDTO.getUsername());
    }

    @Override
    public void updatePassword(UserDTO userDTO) {
        String oldPassword = userDTO.getOldPassword();
        String newPassword = userDTO.getNewPassword();
        String validationMessage = getPasswordValidationMessage(newPassword);
        if (validationMessage == null) {
            userDAO.updatePassword(userDTO.getUsername(), oldPassword, newPassword);
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
