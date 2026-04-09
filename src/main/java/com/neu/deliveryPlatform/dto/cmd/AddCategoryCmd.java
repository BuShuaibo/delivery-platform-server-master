package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author asiawu
 * @Date 2023-04-17 14:18
 * @Description:
 */
@Data
public class AddCategoryCmd {
    @NotBlank(message = "分类名不能为空")
    private String categoryName;
}
