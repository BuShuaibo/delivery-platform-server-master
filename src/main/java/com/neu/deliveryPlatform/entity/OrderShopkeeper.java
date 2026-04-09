package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单-商家中间表
 * @TableName order_shopkeeper
 */
@TableName(value = "sys_order_shopkeeper")
@Data
public class OrderShopkeeper implements Serializable {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商家id
     */
    private Long shopkeeperId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}