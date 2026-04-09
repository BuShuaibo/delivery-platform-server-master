package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author： JiuYou
 * @Date： 2023/4/23
 * @Description：
 */
@Data
public class UpdateRiderLocationCmd {
    @NotBlank(message = "骑手id不能为空")
    private String riderId;
    @NotNull(message = "经度不能为空")
    private Double longitude;
    @NotNull(message = "纬度不能为空")
    private Double latitude;
}
