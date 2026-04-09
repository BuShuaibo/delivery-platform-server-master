package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 * @TableName order
 */
@TableName(value = "sys_order")
@Data
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 下单用户的id
     */
    private Long userId;

    /**
     * 骑手id
     */
    private Long riderId;

    /**
     * 成交金额
     */
    private Double price;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 开始配送时间
     */
    private Date deliveryTime;

    /**
     * 配送结束时间
     */
    private Date endTime;

    /**
     * 订单配送状态 0-商家未接单  1-商家已接单 2-骑手已接单 3-配送完毕
     */
    private Integer status;

    /**
     * 优惠金额
     */
    private Double preferentialPrice;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 订单类型 0-拿快递 1-拿外卖 2-购买商品 3-其他
     */
    private Integer type;

    /**
     * 起点id
     */
    private String startingPointId;

    /**
     * 终点id
     */
    private String terminalId;

    /**
     * 起点地址（字符串）
     */
    private String startingPointAddress;

    /**
     * 终点地址（字符串）
     */
    private String terminalAddress;

    /**
     * 骑手走的总路程(米)
     */
    private Double riderDistance;

    /**
     * 骑手报酬
     */
    private Double riderRemuneration;

    /**
     * 平台抽成
     */
    private Double commission;

    /**
     * 微生活端订单号后四位
     */
    private Integer endNumber;

    /**
     * 下单用户的手机号
     */
    private String userPhoneNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}