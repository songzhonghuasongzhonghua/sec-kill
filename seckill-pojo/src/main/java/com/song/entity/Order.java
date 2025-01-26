package com.song.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class Order {
    private Integer id;
    private String userId;
    private Integer goodsId;
    private Integer deliveryAddId;
    private String goodsName;
    private Integer goodsCount;
    private BigDecimal goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private LocalDateTime createDate;
    private LocalDateTime payData;
}
