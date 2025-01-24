package com.song.controller;

import com.song.constant.JwtConstant;
import com.song.dto.LoginDTO;
import com.song.entity.User;
import com.song.properties.JwtProperties;
import com.song.result.Result;
import com.song.service.UserService;
import com.song.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/login")
    public Result login(@Validated LoginDTO loginDTO) {

        User user = userService.login(loginDTO);

        HashMap<String,Object> map = new HashMap();
        map.put(JwtConstant.USER_ID,user.getId());

        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                map
        );

        return Result.success(token);
    }
}
