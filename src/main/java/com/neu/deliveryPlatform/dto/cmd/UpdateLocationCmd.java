package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author asiawu
 * @Date 2023-04-19 8:09
 * @Description:
 */
@Data
public class UpdateLocationCmd {
    @NotNull(message = "订单id不能为空")
    private Long orderId;
    @NotNull(message = "骑手id不能为空")
    private Long riderId;
    @NotNull(message = "经度不能为空")
    private Double longitude;
    @NotNull(message = "纬度不能为空")
    private Double latitude;
}
