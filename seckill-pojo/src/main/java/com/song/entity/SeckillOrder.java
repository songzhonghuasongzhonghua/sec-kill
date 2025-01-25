package com.song.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrder {

    private Integer id;
    private String userId;
    private Integer goodsId;
    private Integer orderId;
}
