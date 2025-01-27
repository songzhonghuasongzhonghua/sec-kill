package com.song.service.impl;

import com.song.constant.MessageConstant;
import com.song.constant.RedisConstant;
import com.song.context.BaseContext;
import com.song.entity.Order;
import com.song.entity.SeckillGoods;
import com.song.entity.SeckillOrder;
import com.song.exception.OrderFailedException;
import com.song.mapper.OrderMapper;
import com.song.mapper.SeckillGoodsMapper;
import com.song.mapper.SeckillOrderMapper;
import com.song.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class SeckillServiceImpl implements SeckillService  {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public void doSeckill(Integer goodsId,String userId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();

        Integer stockCount = seckillGoodsMapper.getStockCountByGoodsId(goodsId);
        seckillGoodsMapper.updateStockCountByGoodsId(goodsId, stockCount-1);

        if(stockCount < 1){
            valueOperations.set(RedisConstant.SECKILL_STOCK_IS_EMPTY+goodsId,true);
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
                .orderId(order.getId()) //todo 可能有bug
                .build();


        //往redis存储一份秒杀订单
        seckillOrderMapper.insert(seckillOrder); //创建类user_id和goods_id索引，保证一个用户只能抢购同个订单一次
        valueOperations.set(RedisConstant.SECKILL_ORDER_KEY + "user_id"+userId + "goods_id"+goodsId,seckillOrder);
    }

    @Override
    public Integer getResult(Integer goodsId) {
        String userId = BaseContext.getUserId();
        Order order = orderMapper.getOrderByUserIdAndGoodsId(userId, goodsId);
        if (order == null) {
            return 0;
        }else if(redisTemplate.opsForValue().get(RedisConstant.SECKILL_STOCK_IS_EMPTY+goodsId) != null){
            //如果有值，说明库存已空
           return -1;
        }else{
            return 1;
        }

    }


}
