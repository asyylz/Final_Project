package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.Services.Impl.UserServiceImpl;
import com.wgapp.worksheetgenerator.Services.UserService;

public class UserController {

    private final UserService userService = new UserServiceImpl();


    public UserController() {

    }

    public void registerUser(UserDTO userDTO) {
        userService.register(userDTO);

    }


    public void loginUser(UserDTO userDTO) {
        userService.login(userDTO);
    }


    public void setPin(UserDTO userDTO) {
        userService.setPinNumber(userDTO);
    }

    public void updatePassword(UserDTO userDTO) {
        userService.updatePassword(userDTO);
    }

}
