package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Zed
 * @Date 2023/6/16 15:45
 * @Description:
 */
/**
 * 骑手余额明细表
 * @TableName sys_rider_remaining_detail
 */
@TableName(value = "sys_rider_remaining_detail")
@Data
public class RiderRemainingDetail implements Serializable {
    /**
     * 明细Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 产生时间
     */
    private Date createTime;

    /**
     * 1-收入 2-提现
     */
    private Integer type;

    /**
     * 骑手Id
     */
    private Long riderId;

    /**
     * 涉及金额
     */
    private Double money;

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 骑手余额
     */
    private Double remaining;
}
