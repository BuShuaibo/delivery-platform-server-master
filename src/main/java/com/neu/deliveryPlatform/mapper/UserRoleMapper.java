package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author： JiuYou
 * @Date： 2023/4/9
 * @Description：用户权限mapper
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
