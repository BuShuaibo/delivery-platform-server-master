package com.neu.deliveryPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neu.deliveryPlatform.dto.cmd.*;
import com.neu.deliveryPlatform.entity.Permission;
import com.neu.deliveryPlatform.entity.Role;
import com.neu.deliveryPlatform.entity.RolePermission;
import com.neu.deliveryPlatform.mapper.PermissionMapper;
import com.neu.deliveryPlatform.mapper.RoleMapper;
import com.neu.deliveryPlatform.mapper.RolePermissionMapper;
import com.neu.deliveryPlatform.service.RolePermissionService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Response addRole(AddRoleCmd addRoleCmd) {
        Role role=new Role();
        BeanUtils.copyProperties(addRoleCmd,role);
        return roleMapper.addRole(role) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response deleteRole(Integer roleId) {
        return roleMapper.deleteById(roleId) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response updateRole(UpdateRoleCmd updateRoleCmd) {
        Role role=new Role();
        BeanUtils.copyProperties(updateRoleCmd,role);
        return roleMapper.updateById(role) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response getAllRole() {
        return Response.of(roleMapper.selectList(null));
    }

    @Override
    public Response addPermission(AddPermissionCmd addPermissionCmd) {
        Permission permission=new Permission();
        BeanUtils.copyProperties(addPermissionCmd,permission);
        return permissionMapper.addPermission(permission) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response deletePermission(Integer permissionId) {
        return permissionMapper.deleteById(permissionId) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response updatePermission(UpdatePermissionCmd updatePermissionCmd) {
        Permission permission=new Permission();
        BeanUtils.copyProperties(updatePermissionCmd,permission);
        return permissionMapper.updateById(permission) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response getAllPermission() {
        return Response.of(permissionMapper.selectList(null));
    }

    @Override
    public Response addRolePermission(AddRolePermissionCmd addRolePermissionCmd) {
        RolePermission rolePermission=new RolePermission();
        BeanUtils.copyProperties(addRolePermissionCmd,rolePermission);
        return rolePermissionMapper.insert(rolePermission) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response deleteRolePermission(Integer roleId, Integer permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId,roleId);
        wrapper.eq(RolePermission::getPermissionId,permissionId);
        return rolePermissionMapper.delete(wrapper) ==1 ? Response.success() :Response.error();
    }

    @Override
    public Response getAllRolePermission(Integer roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        return Response.of(rolePermissionMapper.selectList(wrapper));
    }
}
