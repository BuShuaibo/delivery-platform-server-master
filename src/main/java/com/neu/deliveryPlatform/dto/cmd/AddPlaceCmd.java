package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author asiawu
 * @Date 2023-04-19 9:27
 * @Description:
 */
@Data
public class AddPlaceCmd {
    @NotNull(message = "经度不能为空")
    private Double longitude;
    @NotNull(message = "纬度不能为空")
    private Double latitude;
    @NotBlank(message = "地点名不能为空")
    private String name;
}
