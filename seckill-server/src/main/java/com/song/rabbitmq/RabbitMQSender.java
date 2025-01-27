package com.song.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 秒杀消息发送器
     * @param message
     */
    public void seckillSendMessage(Object message){
        rabbitTemplate.convertAndSend(RabbitMQConstant.SECKILL_EXCHANGE,RabbitMQConstant.SECKILL_ROUTING_KEY,message);
    }
}
