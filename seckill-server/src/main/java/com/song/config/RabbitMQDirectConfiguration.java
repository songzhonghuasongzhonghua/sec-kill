//package com.song.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//public class RabbitMQDirectConfiguration {
//    private static final String QUEUE_DIRECT_01 = "queue_direct_01";
//    private static final String QUEUE_DIRECT_02 = "queue_direct_02";
//    private static final String EXCHANGE = "direct_exchange";
//    private static final String ROUTING_KEY_01 = "direct_routing_key_01";
//    private static final String ROUTING_KEY_02 = "direct_routing_key_02";
//
//
//    @Bean
//    public Queue queue01() {
//        return new Queue(QUEUE_DIRECT_01);
//    }
//
//    @Bean
//    public Queue queue02(){
//        return new Queue(QUEUE_DIRECT_02);
//    }
//
//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange(EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding01(){
//        return BindingBuilder.bind(queue01()).to(directExchange()).with(ROUTING_KEY_01);
//    }
//
//    @Bean
//    public Binding binding02(){
//        return BindingBuilder.bind(queue02()).to(directExchange()).with(ROUTING_KEY_02);
//    }
//}
