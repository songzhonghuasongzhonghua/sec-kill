package com.song.mapper;

import com.song.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 登陆请求，通过id(phone)查找
     * @param phone
     * @return
     */
    @Select("select * from user where id = #{phone}")
    User getUserByPhone(String phone);

}
