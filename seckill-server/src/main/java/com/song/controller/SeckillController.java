package com.song.controller;

import com.song.result.Result;
import com.song.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {


    @Autowired
    private SeckillService seckillService;


    @PostMapping("/do/{goodsId}")
    public Result doSeckill(@PathVariable Integer goodsId){
        log.info("开启秒杀:{}",goodsId);
        seckillService.doSeckill(goodsId);
        return Result.success();

    }

}
