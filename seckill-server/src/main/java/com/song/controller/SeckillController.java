package com.song.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.tools.json.JSONUtil;
import com.song.constant.MessageConstant;
import com.song.constant.RedisConstant;
import com.song.context.BaseContext;
import com.song.entity.SeckillGoods;
import com.song.exception.OrderFailedException;
import com.song.mapper.SeckillGoodsMapper;
import com.song.mapper.SeckillOrderMapper;
import com.song.rabbitmq.RabbitMQSender;
import com.song.result.Result;
import com.song.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController  implements InitializingBean {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    private Map<Integer,Boolean> isEmptyStock = new HashMap<>();


    @PostMapping("/do/{goodsId}")
    public Result doSeckill(@PathVariable Integer goodsId) throws JsonProcessingException {
        log.info("开启秒杀:{}",goodsId);
        String userId = BaseContext.getUserId();

        //查询秒杀商品库存是否足够
//        Integer stockCount = seckillGoodsMapper.getStockCountByGoodsId(goodsId);
//        if(stockCount < 1){
//             throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
//          }

        //提前判断，减少redis请求
        if(isEmptyStock.get(goodsId)){
            throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
        }
        //查询秒杀商品库存优化，通过redis存储库存量，从而减少服务器操作
        ValueOperations stringOperation = redisTemplate.opsForValue();
        Long stockCount =  stringOperation.decrement("seckill_goods_"+goodsId);
        if(stockCount < 0){
            isEmptyStock.put(goodsId,true);
            stringOperation.increment("seckill_goods_"+goodsId);
            throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
        }

        //查询是否有重复秒杀订单,也可以通过redis缓存秒杀订单信息，用来减少缓存
//        Integer existOrderCount = seckillOrderMapper.getOrdersCountByUserIdAndGoodsId(userId,goodsId);
//        if(existOrderCount > 0){
//            throw new OrderFailedException(MessageConstant.ALEADY_EXIST);
//        }
        //通过redis存储已生成的秒杀订单，从而减少数据库请求
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object seckillOrderFromRedis = valueOperations.get(RedisConstant.SECKILL_ORDER_KEY + "user_id" + userId + "goods_id" + goodsId);
        if(seckillOrderFromRedis != null){
            throw new OrderFailedException(MessageConstant.ALEADY_EXIST);
        }

        //秒杀商品库存减一
//        Integer affectRows =    seckillGoodsMapper.updateStockCountByGoodsId(goodsId,stockCount-1); //此处应判断库存是否等于0
//        if(affectRows == 0){
//            //当影响行数为0时，说明库存已为空，则秒杀失败
//            throw new OrderFailedException(MessageConstant.OUT_OF_STOCK);
//        }

//        seckillService.doSeckill(goodsId);
        //传送goodsid至消息队列
        Map map = new HashMap();
        map.put("goodsId",goodsId);
        map.put("userId",userId);
        ObjectMapper objectMapper = new ObjectMapper();

        rabbitMQSender.seckillSendMessage(objectMapper.writeValueAsString(map));

        return Result.success(0);//0表示排队中-1表示库存已无1表示秒杀成功
    }



    @GetMapping("/result/{goodsId}")
    public Result getSeckillResult(@PathVariable Integer goodsId){
        log.info("获取秒杀订单状态信息{}{}",goodsId,BaseContext.getUserId());
        Integer orderId =  seckillService.getResult(goodsId);
        //orderId 0表示正在排队中 1 表示已成功秒杀 -1表示秒杀失败，已无库存
        return Result.success(orderId);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<SeckillGoods> list = seckillGoodsMapper.list();
        list.forEach(goods -> {
            ValueOperations stringOperation = redisTemplate.opsForValue();
            stringOperation.set("seckill_goods_"+goods.getGoodsId(),goods.getStockCount());
            isEmptyStock.put(goods.getGoodsId(),false);
        });
    }



}
