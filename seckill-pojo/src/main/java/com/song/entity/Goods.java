package com.song.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private Integer id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private BigDecimal goodsPrice;
    private Integer goodsStock;
}
