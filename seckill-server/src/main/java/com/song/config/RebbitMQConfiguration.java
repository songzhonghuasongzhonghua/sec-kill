package com.song.config;

import com.song.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RebbitMQConfiguration {

    private String SECKILL_QUEUE = RabbitMQConstant.SECKILL_QUEUE;
    private String SECKILL_EXCHANGE = RabbitMQConstant.SECKILL_EXCHANGE;


    /**
     * 秒杀队列
     * @return
     */
    @Bean
    public Queue seckillQueue(){
        return new Queue(SECKILL_QUEUE);
    }

    /**
     * 秒杀topic交换机
     * @return
     */
    @Bean
    public TopicExchange seckillExchange(){
        return new TopicExchange(SECKILL_EXCHANGE);
    }

    /*
    绑定队列到交换机
     */
    @Bean
    public Binding seckillBinding(){
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with("seckill.#");
    }
}
