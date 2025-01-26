package com.song.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SeckillGoodsMapper {

    @Select("select stock_count from seckill_goods where goods_id =#{goodsId}")
    Integer getStockCountByGoodsId(Integer goodsId);


    @Update("update seckill_goods set stock_count = #{count} where goods_id =#{goodsId} and stock_count > 0")
    Integer updateStockCountByGoodsId(Integer goodsId,Integer count);
}
