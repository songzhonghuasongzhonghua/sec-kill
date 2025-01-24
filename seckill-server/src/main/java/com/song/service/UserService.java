package com.song.service;

import com.song.dto.LoginDTO;
import com.song.entity.User;


public interface UserService {

    public User login(LoginDTO loginDTO);
}
