package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    int addPermission(Permission permission);
}
