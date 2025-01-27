package com.song.mapper;

import com.song.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    void insert(Order order);


    @Select("select * from `order` where user_id =#{userId} and goods_id=#{goodsId}")
    Order getOrderByUserIdAndGoodsId(String userId,Integer goodsId);
}
