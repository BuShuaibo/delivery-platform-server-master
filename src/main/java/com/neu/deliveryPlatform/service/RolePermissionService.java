package com.neu.deliveryPlatform.service;


import com.neu.deliveryPlatform.dto.cmd.*;
import com.neu.deliveryPlatform.util.Response;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */
public interface RolePermissionService {
    Response addRole(AddRoleCmd addRoleCmd);

    Response deleteRole(Integer roleId);

    Response updateRole(UpdateRoleCmd updateRoleCmd);

    Response getAllRole();

    Response addPermission(AddPermissionCmd addPermissionCmd);


    Response deletePermission(Integer permissionId);

    Response updatePermission(UpdatePermissionCmd updatePermissionCmd);

    Response getAllPermission();

    Response addRolePermission(AddRolePermissionCmd addRolePermissionCmd);

    Response deleteRolePermission(Integer roleId, Integer permissionId);

    Response getAllRolePermission(Integer roleId);
}
