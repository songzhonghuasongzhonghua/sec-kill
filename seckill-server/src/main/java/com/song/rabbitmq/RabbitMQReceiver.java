package com.song.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.song.constant.MessageConstant;
import com.song.constant.RabbitMQConstant;
import com.song.constant.RedisConstant;
import com.song.context.BaseContext;
import com.song.entity.SeckillGoods;
import com.song.exception.OrderFailedException;
import com.song.mapper.SeckillGoodsMapper;
import com.song.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


@Slf4j
@Component
public class RabbitMQReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues = RabbitMQConstant.SECKILL_QUEUE)
    public void seckillReceive(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(message, new TypeReference<>() {});
        String userId = (String) map.get("userId");
        Integer goodsId = (Integer) map.get("goodsId");
        log.info("seckill队列接受到的信息：{}{}",userId,goodsId);

        //查询库存
        Integer stockCount = seckillGoodsMapper.getStockCountByGoodsId(goodsId);
        if(stockCount < 0){
//            throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
            return;
        }

        //查询是否有已存在订单
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object seckillOrderFromRedis = valueOperations.get(RedisConstant.SECKILL_ORDER_KEY + "user_id" + userId + "goods_id" + goodsId);
        if(seckillOrderFromRedis != null){
//            throw new OrderFailedException(MessageConstant.ALEADY_EXIST);
            return;
        }
        //上述检查都没有问题则下单
        seckillService.doSeckill(goodsId,userId);

    }
}
