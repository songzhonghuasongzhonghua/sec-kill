package com.song.service.impl;

import com.song.mapper.GoodsMapper;
import com.song.service.GoodsService;
import com.song.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsVO> getList() {

        return goodsMapper.getList();
    }

    @Override
    public GoodsVO getById(Integer goodsId) {
        return goodsMapper.getGoodsById(goodsId);
    }
}
