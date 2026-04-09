package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商家-商品中间表
 * @TableName shopkeeper_commodity
 */
@TableName(value = "sys_shopkeeper_commodity")
@Data
public class ShopkeeperCommodity implements Serializable {
    /**
     * 商家id
     */
    private Long shopkeeperId;

    /**
     * 商品id
     */
    private Long commodityId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}