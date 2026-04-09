package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
@Data
public class AddRoleCmd {
    /**
     * 角色关键字
     */
    @NotEmpty(message = "角色关键字不能为空")
    private String roleKey;

    /**
     * 角色名
     */
    @NotEmpty(message = "角色名不能为空")
    private String name;
}
