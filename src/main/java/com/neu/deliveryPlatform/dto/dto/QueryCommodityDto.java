package com.neu.deliveryPlatform.dto.dto;

import com.neu.deliveryPlatform.entity.Commodity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author asiawu
 * @Date 2023-04-06 15:02
 * @Description:
 */
@ApiModel(value = "商品信息", description = "商品信息(包括商品所属商家的id)", parent = Commodity.class)
@Data
public class QueryCommodityDto {
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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 销量
     */
    private Integer sales;

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
    private List<Long> shopkeeperIds;

}
