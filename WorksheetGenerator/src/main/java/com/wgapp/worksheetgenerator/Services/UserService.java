package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.DTOs.UserDTO;

public interface UserService {
    UserDTO register(UserDTO userDTO);
    UserDTO login(UserDTO userDTO)  ;
}
