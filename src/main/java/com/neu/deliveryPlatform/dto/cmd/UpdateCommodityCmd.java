package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import java.util.List;

/**
 * @Author asiawu
 * @Date 2023-04-06 14:36
 * @Description:
 */
@Data
public class UpdateCommodityCmd {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 上架状态
     */
    private Integer isAvailable;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 商品属于的类别的id
     */
    private Long categoryId;

    /**
     * 商品所属商家的id
     */
    private List<Long> shopkeeperIds;
}
