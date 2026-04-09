package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName category
 */
@TableName(value = "sys_category")
@Data
public class Category implements Serializable {
    /**
     * 商品类别id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品类别名
     */
    private String categoryName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}