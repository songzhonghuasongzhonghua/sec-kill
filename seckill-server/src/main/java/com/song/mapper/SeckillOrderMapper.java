package com.song.mapper;

import com.song.entity.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SeckillOrderMapper {

    @Select("select count(id) from seckill_order where user_id = #{userId} and goods_id=#{goodsId}")
    Integer getOrdersCountByUserIdAndGoodsId(String userId, Integer goodsId);



    void insert(SeckillOrder seckillOrder);
}
