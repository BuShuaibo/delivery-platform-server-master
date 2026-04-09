package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家表
 * @TableName shopkeeper
 */
@TableName(value = "sys_shopkeeper")
@Data
public class Shopkeeper implements Serializable {
    /**
     * 商家id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 总销量
     */
    private Integer sales;

    /**
     * 店名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 电话
     */
    private String phone;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 开始营业时间
     */
    private Date startTime;

    /**
     * 结束营业时间
     */
    private Date endTime;

    /**
     * 是否正常营业
     */
    private Integer isAvailable;

    /**
     * 位置id
     */
    private String PlaceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}