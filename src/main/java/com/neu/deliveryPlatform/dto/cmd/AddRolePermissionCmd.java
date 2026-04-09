package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/23
 * {@code @Description: }
 */
@Data
public class AddRolePermissionCmd {
    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    private Integer roleId;

    /**
     *权限id
     */
    @NotNull(message = "权限id不能为空")
    private Integer permissionId;
}
