package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： JiuYou
 * @Date： 2023/4/9
 * @Description：用户角色
 */
@TableName(value = "sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private Long userId;
    private Integer roleId;
}
