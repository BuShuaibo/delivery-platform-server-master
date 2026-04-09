package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
@Data
public class AddUserRoleCmd {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     *角色id
     */
    @NotNull(message = "角色id不能为空")
    private Integer roleId;
}
