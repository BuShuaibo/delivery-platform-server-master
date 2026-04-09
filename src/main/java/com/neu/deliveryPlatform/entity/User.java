package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value = "sys_user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 小程序唯一标识
     */
    private String openid;

    /**
     * 姓名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 学号(骑手)
     */
    private Integer studentId;

    /**
     * 商家id 不对应商家为null
     */
    private Long shopkeeperId;

    /**
     * 总接单量
     */
    private Integer totalOrderQuantity;

    /**
     * 订单完成量
     */
    private Integer orderFulfillment;

    /**
     * 骑手状态是否激活
     */
    private Boolean riderActive;

    /**
     * 骑手余额
     */
    private Double riderRemaining;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}