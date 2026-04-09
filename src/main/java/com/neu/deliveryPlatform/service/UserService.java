package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.dto.cmd.*;
import com.neu.deliveryPlatform.dto.qry.AdminLoginQry;
import com.neu.deliveryPlatform.entity.User;
import com.neu.deliveryPlatform.util.Response;

/**
* @author Asia
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-04-05 17:25:22
*/
public interface UserService {

    Response getById(Long id);

    Response selectPageUsers(Integer currentPage, Integer pageSize);

    Response updateUserById(UpdateUserCmd updateUserCmd);

    Response addUser(AddUserCmd addUserCmd);


    public Response wxLogin(String code, AddUserCmd addUserCmd);

    public Response getByOpenid(String openid);

    public boolean addUserForShopkeeper(User user, Long shopkeeperId);

    Response adminLogin(AdminLoginQry adminLoginQry);

    Response getOrdersByUserId(Long userId, Integer currentPage, Integer pageSize);

    Response getOrdersByRiderId(Long userId, Integer currentPage, Integer pageSize);

    Response updateRiderLocation(UpdateRiderLocationCmd updateRiderLocationCmd);

    Response selectRiders();

    Response updateRiderStatus(Long id);

    Response clearRiderRemaining(ClearRiderRemainingCmd clearRiderRemainingCmd);

    Response updateRiderRemaining(Long id, Double riderRemaining);

    Response addUserRole(AddUserRoleCmd addUserRoleCmd);

    Response deleteUserRole(Long userId, Integer roleId);

    Response getAllUserRole(Long userId);

    Response refreshToken(String refreshToken);

    Response getTemporaryWithdrawalCode();
}
