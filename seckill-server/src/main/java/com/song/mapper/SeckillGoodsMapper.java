package com.song.mapper;

import com.song.entity.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SeckillGoodsMapper {

    @Select("select stock_count from seckill_goods where goods_id =#{goodsId}")
    Integer getStockCountByGoodsId(Integer goodsId);


    @Update("update seckill_goods set stock_count = #{count} where goods_id =#{goodsId} and stock_count > 0")
    Integer updateStockCountByGoodsId(Integer goodsId,Integer count);

    @Select("select * from seckill_goods")
    List<SeckillGoods> list();
}
