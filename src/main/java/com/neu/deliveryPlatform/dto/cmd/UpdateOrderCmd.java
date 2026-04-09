package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @Author Zed
 * @Date 2023/4/7 10:26
 * @Description:
 */
@Data
public class UpdateOrderCmd {
    @NotNull(message = "id不能为空")
    private Long id;
    @NotNull(message = "目标状态不能为空")
    @Max(4L)
    private Integer targetStatus;
    private Long riderId;
    private Integer orderEndNumber;
}
