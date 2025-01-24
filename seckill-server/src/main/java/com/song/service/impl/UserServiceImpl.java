package com.song.service.impl;

import com.song.constant.MessageConstant;
import com.song.dto.LoginDTO;
import com.song.entity.User;
import com.song.exception.LoginFailedException;
import com.song.mapper.UserMapper;
import com.song.service.UserService;
import com.song.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User login(LoginDTO loginDTO) {
        String phone = loginDTO.getPhone();
        String password = loginDTO.getPassword();

        User user  = userMapper.getUserByPhone(phone);
        if(user == null){
            throw new LoginFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        String passwordMd5 = Md5Util.getMd5(password);
        if(!passwordMd5.equals(user.getPassword())){
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }


        return user;
    }
}
