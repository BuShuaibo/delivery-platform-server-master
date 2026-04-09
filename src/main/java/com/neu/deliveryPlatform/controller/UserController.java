package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.AddUserCmd;
import com.neu.deliveryPlatform.dto.cmd.AddUserRoleCmd;
import com.neu.deliveryPlatform.dto.cmd.ClearRiderRemainingCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateUserCmd;
import com.neu.deliveryPlatform.dto.qry.AdminLoginQry;
import com.neu.deliveryPlatform.dto.qry.GetOrderByRiderIdQry;
import com.neu.deliveryPlatform.dto.qry.GetOrderByUserIdQry;
import com.neu.deliveryPlatform.service.UserService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author asiawu
 * @Date 2023-04-05 0:40
 * @Description:
 */

@Api(tags = "用户")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @ApiOperation(value = "更新骑手实时位置", notes = "骑手在线时，一段时间向后台发送请求，携带骑手id，经度和纬度，更新骑手状态数据")
//    @PostMapping("/updateRiderLocation")
//    public Response updateRiderLocation(@Valid @RequestBody UpdateRiderLocationCmd updateRiderLocationCmd) {
//        return userService.updateRiderLocation(updateRiderLocationCmd);
//    }


    @ApiOperation(value = "用户查询订单", notes = "根据用户id分页查询订单")
    @GetMapping("/getOrdersByUserId")
    public Response getOrdersByUserId(GetOrderByUserIdQry getOrderByUserIdQry) {
        return userService.getOrdersByUserId(getOrderByUserIdQry.getUserId(), getOrderByUserIdQry.getCurrentPage(), getOrderByUserIdQry.getPageSize());
    }

    @ApiOperation(value = "骑手查询订单", notes = "根据骑手id分页查询订单")
    @GetMapping("/getOrdersByRiderId")
    public Response getOrdersByRiderId(GetOrderByRiderIdQry getOrderByRiderIdQry) {
        return userService.getOrdersByRiderId(getOrderByRiderIdQry.getRiderId(), getOrderByRiderIdQry.getCurrentPage(), getOrderByRiderIdQry.getPageSize());
    }

    @ApiOperation(value = "根据id获取用户信息", notes = "根据传入的用户id查询用户信息")
    @GetMapping("/getById")
    public Response getById(Long id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "获取全部用户", notes = "分页查询全部用户")
    @GetMapping("/getAll")
    public Response getAllUsers(Integer currentPage, Integer pageSize) {
        return userService.selectPageUsers(currentPage, pageSize);
    }

    @ApiOperation(value = "获取所有骑手信息",
            notes = "获取所有骑手信息，包括骑手订单完成量、完成率、空闲状态")
    @GetMapping("/getAllRider")
    public Response getAllRider() {
        return userService.selectRiders();
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping("/updateById")
    public Response updateUserById(@Valid @RequestBody UpdateUserCmd updateUserCmd) {
        return userService.updateUserById(updateUserCmd);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping("/add")
    public Response addUser(@Valid @RequestBody AddUserCmd addUserCmd) {
        return userService.addUser(addUserCmd);
    }

    @ApiOperation(value = "微信用户注册/登录",
            notes = "用户登录，传入js_code；如果是新用户注册，需要传入注册用户的信息")
    @PostMapping("/login")
    public Response login(@RequestParam("js_code") String code, @Valid @RequestBody(required = false) AddUserCmd addUserCmd) {
        return userService.wxLogin(code, addUserCmd);
    }

    @ApiOperation(value = "后台管理员登录", notes = "后台管理员登录，输入账号和密码")
    @PostMapping("/adminLogin")
    public Response adminLogin(@Valid @RequestBody AdminLoginQry adminLoginQry) {
        return userService.adminLogin(adminLoginQry);
    }

    @ApiOperation(value = "激活/冻结骑手", notes = "如果骑手是激活状态就冻结，否则就激活")
    @PostMapping("/updateRiderStatus")
    public Response updateRiderStatus(@RequestParam @Valid @NotNull Long id) {
        return userService.updateRiderStatus(id);
    }

    @ApiOperation(value = "清空骑手余额", notes = "将骑手余额直接设为0.00")
    @PostMapping("/clearRiderRemaining")
    public Response clearRiderRemaining(@RequestBody @Valid ClearRiderRemainingCmd clearRiderRemainingCmd) {
        return userService.clearRiderRemaining(clearRiderRemainingCmd);
    }


    @ApiOperation(value = "新增用户角色", notes = "新增用户角色")
    @PostMapping("/addUserRole")
    public Response addUserRole(@Valid @RequestBody AddUserRoleCmd addUserRoleCmd) {
        return userService.addUserRole(addUserRoleCmd);
    }

    @ApiOperation(value = "删除用户角色", notes = "删除用户角色")
    @PostMapping("/deleteUserRole")
    public Response deleteUserRole(@RequestParam @NotNull Long userId, @RequestParam @NotNull Integer roleId) {
        return userService.deleteUserRole(userId, roleId);
    }

    @ApiOperation(value = "获取用户所有角色", notes = "获取用户所有角色")
    @PostMapping("/getAllUserRole")
    public Response getAllUserRole(@RequestParam @Valid @NotNull Long userid) {
        return userService.getAllUserRole(userid);
    }

    @ApiOperation(value = "获取新的refreshToken和accessToken",
            notes = "根据refreshToken获取新的refreshToken和accessToken")
    @GetMapping("/refreshToken")
    public Response refreshToken(@RequestParam String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @ApiOperation(value = "获取临时提现码", notes = "根据用户id获取提现码")
    @GetMapping("/getTemporaryWithdrawalCode")
    public Response getTemporaryWithdrawalCode() {
        return userService.getTemporaryWithdrawalCode();
    }
}
