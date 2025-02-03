package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.DAO.Entities.UserEntity;
import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.ModelsUI.UserProperty;
import com.wgapp.worksheetgenerator.Services.Impl.UserServiceImpl;
import com.wgapp.worksheetgenerator.Services.UserService;

public class UserController {

    private final UserService userService = new UserServiceImpl();


    public UserController() {

    }

    public void registerUser(UserProperty userProperty) {
        // From userPropertyDTO to UserEntity
        UserEntity userEntity = new UserEntity(userProperty.getUsername(), userProperty.getPassword());
        userService.register(userEntity);

    }

    public UserProperty loginUser(UserProperty userProperty) {
        // From userPropertyDTO to UserEntity
        UserEntity userEntity = new UserEntity(userProperty.getUsername(), userProperty.getPassword());
        userEntity = userService.login(userEntity);

        return new UserProperty(userEntity.getId(), userEntity.getUsername(), userEntity.getPinNumber());
    }


    public UserProperty setPin(UserProperty userProperty) {
        UserEntity userEntity = new UserEntity(
                userProperty.getUserId(),
                userProperty.getUsername(),
                userProperty.getPassword(),
                userProperty.getPinNumber());

        userService.setPinNumber(userEntity);

        return new UserProperty(userEntity.getId(), userEntity.getUsername(), userEntity.getPinNumber());

    }

    public void updatePassword(UserProperty userProperty) {

        UserEntity userEntity = new UserEntity(
                userProperty.getUserId(),
                userProperty.getUsername(),
                userProperty.getPassword(),
                userProperty.getNewPassword()
        );
        userService.updatePassword(userEntity);
    }

}
