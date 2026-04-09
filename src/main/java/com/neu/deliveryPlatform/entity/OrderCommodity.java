package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单-商品中间表
 * @TableName order_commodity
 */
@TableName(value = "sys_order_commodity")
@Data
public class OrderCommodity implements Serializable {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long commodityId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}