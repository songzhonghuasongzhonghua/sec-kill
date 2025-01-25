package com.song.mapper;

import com.song.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    List<GoodsVO> getList();


    GoodsVO getGoodsById(Integer goodsId);
}
