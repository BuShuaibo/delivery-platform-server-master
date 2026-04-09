package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author Zed
 * @Date 2023/4/6 20:59
 * @Description:
 */
@Data
public class AddOrderCmd {
    /**
     * 下单用户的id
     */
    @NotNull(message = "下单用户的id不能为空")
    private Long userId;

    /**
     * 成交金额
     */
    @NotNull(message = "成交金额不能为空")
    private Double price;

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
    @NotNull(message = "订单类型不能为空")
    private Integer type;

    /**
     * 商家id
     */
    @NotNull(message = "商家id不能为空")
    private List<Long> shopkeeperIds;

    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    private List<Long> commodityIds;

    /**
     * 起点id
     */
//    @NotNull(message = "起点id不能为空")
//    private String startingPointId;

    /**
     * 终点id
     */
//    @NotNull(message = "终点id不能为空")
//    private String terminalId;

    /**
     * 起点地址（字符串）
     */
    private String startingPointAddress;

    /**
     * 终点地址（字符串）
     */
    @NotNull(message = "终点不能为空")
    private String terminalAddress;

    /**
     * 微生活端订单号后四位
     */
    @NotNull(message = "订单尾号后四位不能为空")
    private Integer endNumber;

    /**
     * 下单用户的手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String userPhoneNumber;
}
