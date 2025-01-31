package com.wgapp.worksheetgenerator.Services.Impl;

import com.wgapp.worksheetgenerator.Database.UserEntity;
import com.wgapp.worksheetgenerator.Database.UserDAOImpl;
import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.Services.UserService;

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
        System.out.println("Hello from user service");
        userDAO.setPinNumber(userDTO.getPinNumber(), userDTO.getUsername());
    }




//
//    private boolean isValidPassword(String password) {
//        // Regular expression: At least 6 characters and at least one uppercase letter
//        String passwordPattern = "^(?=.*[A-Z]).{6,}$";
//        return Pattern.matches(passwordPattern, password);
//    }

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
