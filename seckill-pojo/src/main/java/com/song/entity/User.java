package com.song.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private String id; //电话号码
    private String nickname;
    private String password;
    private String slat;
    private String head;
    private LocalDateTime registerDate;
    private LocalDateTime lastLoginDate;
    private Integer loginCount;

}
