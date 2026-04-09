package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    int addRole(Role role);
}
