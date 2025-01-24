package com.song.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
//    @Pattern(regexp = "") 校验可以写上手机的正则
    private String phone;
    @NotNull
    private String password;
}
