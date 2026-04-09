package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author asiawu
 * @Date 2023-04-19 0:11
 * @Description:
 */
@Data
public class UpdatePlaceCmd {
    private Double longitude;
    private Double latitude;
    @NotBlank(message = "地点id不能为空")
    private String id;
    private String name;
}
