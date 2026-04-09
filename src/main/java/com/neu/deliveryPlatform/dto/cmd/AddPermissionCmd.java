package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/23
 * {@code @Description: }
 */
@Data
public class AddPermissionCmd {
    /**
     * 权限名
     */
    @NotEmpty(message="权限名不能为空")
    private String name;

    /**
     * 权限关键字不能为空
     */
    @NotEmpty(message = "权限关键字不能为空")
    private String permissionKey;
}
