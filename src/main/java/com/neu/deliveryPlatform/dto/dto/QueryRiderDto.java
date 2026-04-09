package com.neu.deliveryPlatform.dto.dto;

import lombok.Data;

import java.util.Date;

/**
 * {@code @Author} asiawu
 * {@code @Date} 2023-04-26 13:50
 * {@code @Description:}
 */
@Data
public class QueryRiderDto {
    /**
     * 用户id
     */
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
     * 总接单量
     */
    private Integer totalOrderQuantity;

    /**
     * 订单完成量
     */
    private Integer orderFulfillment;

    /**
     * 订单完成率
     */
    private Integer orderCompletionRate;
    /**
     * 空闲状态
     */
    private Boolean available;

    /**
     * 骑手是否激活
     */
    private Boolean riderActive;

    /**
     * 骑手余额
     */
    private Double riderRemaining;
}
