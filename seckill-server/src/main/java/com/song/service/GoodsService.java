package com.song.service;

import com.song.vo.GoodsVO;

import java.util.List;

public interface GoodsService {


    List<GoodsVO> getList();

    GoodsVO getById(Integer goodsId);
}
