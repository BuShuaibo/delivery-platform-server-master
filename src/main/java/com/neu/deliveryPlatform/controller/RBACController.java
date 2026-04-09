package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.*;
import com.neu.deliveryPlatform.service.RolePermissionService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * {@code @Author: } WilliamJiangrx
 * {@code @Date: } 2023/5/22
 * {@code @Description: }
 */

@Api(tags = "角色权限")
@RestController
@RequestMapping("/api/rbac")
public class RBACController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping("/addRole")
    public Response addRole(@Valid @RequestBody AddRoleCmd addRoleCmd) {
        return rolePermissionService.addRole(addRoleCmd);
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping("/deleteRole")
    public Response deleteRole(@RequestParam @Valid @NotNull Integer roleId) {
        return rolePermissionService.deleteRole(roleId);
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PostMapping("/updateRole")
    public Response updateRole(@Valid @RequestBody UpdateRoleCmd updateRoleCmd) {
        return rolePermissionService.updateRole(updateRoleCmd);
    }

    @ApiOperation(value = "获取所有角色", notes = "获取所有角色")
    @PostMapping("/getAllRole")
    public Response getAllRole() {
        return rolePermissionService.getAllRole();
    }

    @ApiOperation(value = "新增权限", notes = "新增权限")
    @PostMapping("/addPermission")
    public Response addPermission(@Valid @RequestBody AddPermissionCmd addPermissionCmd) {
        return rolePermissionService.addPermission(addPermissionCmd);
    }

    @ApiOperation(value = "删除权限", notes = "删除权限")
    @PostMapping("/deletePermission")
    public Response deletePermission(@RequestParam @Valid @NotNull Integer permissionId) {
        return rolePermissionService.deletePermission(permissionId);
    }

    @ApiOperation(value = "修改权限", notes = "修改权限")
    @PostMapping("/updatePermission")
    public Response updatePermission(@Valid @RequestBody UpdatePermissionCmd updatePermissionCmd) {
        return rolePermissionService.updatePermission(updatePermissionCmd);
    }

    @ApiOperation(value = "获取所有权限", notes = "获取所有权限")
    @PostMapping("/getAllPermission")
    public Response getAllPermission() {
        return rolePermissionService.getAllPermission();
    }

    @ApiOperation(value = "新增角色权限", notes = "新增角色权限")
    @PostMapping("/addRolePermission")
    public Response addRolePermission(@Valid @RequestBody AddRolePermissionCmd addRolePermissionCmd) {
        return rolePermissionService.addRolePermission(addRolePermissionCmd);
    }

    @ApiOperation(value = "删除角色权限", notes = "删除角色权限")
    @PostMapping("/deleteRolePermission")
    public Response deleteRolePermission(@RequestParam @NotNull Integer roleId,@RequestParam @NotNull Integer permissionId) {
        return rolePermissionService.deleteRolePermission(roleId,permissionId);
    }

    @ApiOperation(value = "获取角色所有权限", notes = "获取角色所有权限")
    @PostMapping("/getAllRolePermission")
    public Response getAllRolePermission(@RequestParam @Valid @NotNull Integer roleId) {
        return rolePermissionService.getAllRolePermission(roleId);
    }
}
