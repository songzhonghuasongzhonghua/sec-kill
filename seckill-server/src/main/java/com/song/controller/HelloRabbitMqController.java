//package com.song.controller;
//
//import com.song.rabbitmq.RabbitMqSend;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Queue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//@RequestMapping("/rabbit")
//public class HelloRabbitMqController {
//
//    @Autowired
//    private RabbitMqSend rabbitMqSend;
//
//    @GetMapping("/send")
//    public void send(){
//        rabbitMqSend.sendDirect02("hello rabbitmq");
//    }
//}
