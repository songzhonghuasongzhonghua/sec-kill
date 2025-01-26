package com.song.service.impl;

import com.song.constant.MessageConstant;
import com.song.context.BaseContext;
import com.song.entity.Order;
import com.song.entity.SeckillOrder;
import com.song.exception.OrderFailedException;
import com.song.mapper.OrderMapper;
import com.song.mapper.SeckillGoodsMapper;
import com.song.mapper.SeckillOrderMapper;
import com.song.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private OrderMapper orderMapper;

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
        Integer affectRows =    seckillGoodsMapper.updateStockCountByGoodsId(goodsId,stockCount-1); //此处应判断库存是否等于0
        if(affectRows == 0){
            //当影响行数为0时，说明库存已为空，则秒杀失败
            throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
        }
        //先创建订单
        Order order = Order.builder()
                .userId(userId)
                .goodsId(goodsId)
                .deliveryAddId(0)
                .goodsCount(1)
                .orderChannel(1)
                .status(0)
                .payData(null)
                .goodsName(null)
                .createDate(LocalDateTime.now())
                .build();
        orderMapper.insert(order);

        //创建秒杀订单
        SeckillOrder seckillOrder = SeckillOrder.builder()
                .userId(userId)
                .goodsId(goodsId)
                .orderId(0)
                .build();
        seckillOrderMapper.insert(seckillOrder); //创建类user_id和goods_id索引，保证一个用户只能抢购同个订单一次

    }
}
