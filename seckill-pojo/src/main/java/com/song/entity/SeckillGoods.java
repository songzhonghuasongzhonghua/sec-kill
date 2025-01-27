package com.song.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SeckillGoods {

    private Integer id;
    private Integer goodsId;
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
