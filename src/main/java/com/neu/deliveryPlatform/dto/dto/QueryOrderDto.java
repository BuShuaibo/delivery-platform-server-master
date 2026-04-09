package com.neu.deliveryPlatform.dto.dto;

import com.neu.deliveryPlatform.entity.Order;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "订单信息", description = "订单信息(将相关的id改为name,添加商品名和商家名)", parent = Order.class)
public class QueryOrderDto {
    /**
     * 订单id
     */
    private Long id;

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
     * 订单备注
     */
    private String remark;

    /**
     * 订单类型 0-拿快递 1-拿外卖 2-购买商品 3-其他
     */
    private Integer type;

    /**
     * 起点地址（字符串）
     */
    private String startingPointAddress;

    /**
     * 终点地址（字符串）
     */
    private String terminalAddress;

    /**
     * 骑手报酬
     */
    private Double riderRemuneration;

    /**
     * 平台抽成
     */
    private Double commission;

    /**
     * 下单用户名
     */
    private String userName;

    /**
     * 骑手名
     */
    private String riderName;

    /**
     * 下单用户的手机号
     */
    private String userPhoneNumber;

    /**
     * 商品名
     */
    private List<String> commodityNames;

    /**
     * 商家名
     */
    private List<String> shopkeeperNames;
}