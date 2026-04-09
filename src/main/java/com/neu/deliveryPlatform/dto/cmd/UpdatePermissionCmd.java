package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/23
 * {@code @Description: }
 */
@Data
public class UpdatePermissionCmd {
    /**
     * 权限id
     */
    @NotNull(message="权限id不能为空")
    private Integer id;

    /**
     * 权限关键字
     */
    private String permissionKey;

    /**
     * 权限名
     */
    private String name;
}
