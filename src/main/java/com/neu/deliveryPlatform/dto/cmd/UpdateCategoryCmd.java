package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author asiawu
 * @Date 2023-04-17 14:30
 * @Description:
 */
@Data
public class UpdateCategoryCmd {
    @NotNull(message = "id不能为空")
    private Long id;
    @NotBlank(message = "分类名不能为空")
    private String categoryName;
}
