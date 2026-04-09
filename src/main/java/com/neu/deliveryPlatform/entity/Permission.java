package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
@TableName(value = "sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Integer id;
    @TableField(value = "perm_key")
    private String permissionKey;
    private String name;
}
