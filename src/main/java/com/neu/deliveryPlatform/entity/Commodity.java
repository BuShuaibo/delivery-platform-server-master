package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.neu.deliveryPlatform.annotion.ExcelColum;
import com.neu.deliveryPlatform.properties.ExcelColumnTransferType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品表
 * @TableName commodity
 */
@TableName(value = "sys_commodity")
@Data
public class Commodity implements Serializable {
    /**
     * 商品id
     */
    @ExcelColum(columnTransferType = ExcelColumnTransferType.SUBSTRING)
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 商品名
     */
    @ExcelColum
    private String name;

    /**
     * 商品描述
     */
    @ExcelColum
    private String description;

    /**
     * 商品价格
     */
    @ExcelColum
    private Double price;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 销量
     */
    @ExcelColum
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
    @ExcelColum(columnTransferType = ExcelColumnTransferType.QUERY_FROM_DB)
    private Long categoryId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}