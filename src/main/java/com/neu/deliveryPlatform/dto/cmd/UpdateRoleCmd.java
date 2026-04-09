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
public class UpdateRoleCmd {
    /**
     * 角色id
     */
    @NotNull(message="角色id不能为空")
    private Integer id;

    /**
     * 角色关键字
     */
    private String roleKey;

    /**
     * 角色名
     */
    private String name;
}
