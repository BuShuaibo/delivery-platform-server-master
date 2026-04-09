package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author asiawu
 * @Date 2023-04-17 18:30
 * @Description:
 */
@Data
public class UpdateShopkeeperCmd {
    /**
     * 商家id
     */
    @NotNull(message = "id不能为空")
    private Long id;

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
    private String placeId;
}
