package com.song.service.impl;

import com.song.constant.MessageConstant;
import com.song.context.BaseContext;
import com.song.entity.SeckillOrder;
import com.song.exception.OrderFailedException;
import com.song.mapper.SeckillGoodsMapper;
import com.song.mapper.SeckillOrderMapper;
import com.song.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public void doSeckill(Integer goodsId) {
        //查询秒杀商品库存是否足够
        Integer stockCount = seckillGoodsMapper.getStockCountByGoodsId(goodsId);
        if(stockCount < 1){
            throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
        }
        String userId = BaseContext.getUserId();
        //查询是否有重复秒杀订单
        Integer existOrderCount = seckillOrderMapper.getOrdersCountByUserIdAndGoodsId(userId,goodsId);
        if(existOrderCount > 0){
            throw new OrderFailedException(MessageConstant.ALEADY_EXIST);
        }
        //秒杀商品库存减一
        seckillGoodsMapper.updateStockCountByGoodsId(goodsId,stockCount-1);
        //创建秒杀订单
        SeckillOrder seckillOrder = SeckillOrder.builder()
                .userId(userId)
                .goodsId(goodsId)
                .orderId(0) //todo: 完善此处orderId
                .build();
        seckillOrderMapper.insert(seckillOrder);

    }
}
