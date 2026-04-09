package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/23
 * {@code @Description: }
 */
@TableName(value = "sys_role_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @TableField(value = "role_id")
    private Integer roleId;
    @TableField(value = "menu_id")
    private Integer permissionId;
}
