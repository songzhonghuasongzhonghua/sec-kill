package com.song.controller;

import com.song.context.BaseContext;
import com.song.result.Result;
import com.song.service.GoodsService;
import com.song.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public Result<List<GoodsVO>> list(){
        List<GoodsVO> goodsList = goodsService.getList();
        return Result.success(goodsList);
    }


    @GetMapping("/info/{goodId}")
    public Result<GoodsVO> info(@PathVariable Integer goodId){
        GoodsVO goodsVO = goodsService.getById(goodId);
        log.info("用户id"+ BaseContext.getUserId());
        return Result.success(goodsVO);
    }
}
