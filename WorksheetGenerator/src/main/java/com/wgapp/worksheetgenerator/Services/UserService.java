package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.DAO.Entities.UserEntity;

public interface UserService {
    void register(UserEntity userEntity);
    UserEntity login(UserEntity userEntity)  ;
    void setPinNumber(UserEntity userEntity);
    void updatePassword(UserEntity userEntity);
}
