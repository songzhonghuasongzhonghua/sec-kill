package com.song.service;

public interface SeckillService {

    void doSeckill(Integer goodsId,String userId);

    Integer getResult(Integer goodsId);
}
