//package com.song.config;
//
//import com.rabbitmq.client.ConnectionFactory;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//
//@Configuration
//public class RabbitMqConfiguration {
//
//    private static final String QUEUE01= "queue_fanout_01";
//    private static final String QUEUE02= "queue_fanout_02";
//
//    private static final String FANOUT_EXCHANGE = "fanout_exchange"; //广播交换机
//
//    @Bean
//    public Queue queue() {
//        return new Queue("queue",true);
//    }
//
//    @Bean
//    public Queue queue01(){
//        return new Queue(QUEUE01);
//    }
//
//    @Bean
//    public Queue queue02(){
//        return new Queue(QUEUE02);
//    }
//
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(FANOUT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding01() {
//        return BindingBuilder.bind(queue01()).to(fanoutExchange());
//    }
//
//    @Bean
//    public Binding binding02() {
//        return BindingBuilder.bind(queue02()).to(fanoutExchange());
//    }
//}
