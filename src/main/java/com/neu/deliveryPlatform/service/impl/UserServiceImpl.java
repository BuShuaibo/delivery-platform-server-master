package com.neu.deliveryPlatform.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.dto.cmd.*;
import com.neu.deliveryPlatform.dto.dto.QueryRiderDto;
import com.neu.deliveryPlatform.dto.qry.AdminLoginQry;
import com.neu.deliveryPlatform.entity.*;
import com.neu.deliveryPlatform.filter.JwtAuthenticationTokenFilter;
import com.neu.deliveryPlatform.mapper.OrderMapper;
import com.neu.deliveryPlatform.mapper.RiderRemainingDetailMapper;
import com.neu.deliveryPlatform.mapper.UserMapper;
import com.neu.deliveryPlatform.mapper.UserRoleMapper;
import com.neu.deliveryPlatform.properties.*;
import com.neu.deliveryPlatform.service.UserService;
import com.neu.deliveryPlatform.util.CommonUtil;
import com.neu.deliveryPlatform.util.JwtUtil;
import com.neu.deliveryPlatform.util.Response;
import com.neu.deliveryPlatform.util.WxCode2SessionUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author Asia
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-04-05 17:25:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AuthorityProperties authorityProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private RiderRemainingDetailMapper riderRemainingDetailMapper;

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String USERNAME = "username";
    private static final String USER_ID = "userId";

    @Override
    public Response updateRiderLocation(UpdateRiderLocationCmd updateRiderLocationCmd) {
        String riderId = updateRiderLocationCmd.getRiderId();
        List<String> roles = userMapper.getRoles(Long.valueOf(riderId));
        if (!roles.contains(KeyProperties.ROLE_USER_KEY)) {
            throw new BizException(ErrorCode.ID_MISS);
        }
        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
        Location location = new Location();
        location.setLongitude(updateRiderLocationCmd.getLongitude());
        location.setLatitude(updateRiderLocationCmd.getLatitude());
        Map<String, Object> riderInfo = new HashMap<String, Object>() {{
            put(KeyProperties.LOCATION_LOCATION_KEY, location);
            put(KeyProperties.LOCATION_RIDER_AVAILABLE_KEY, true);
        }};
        operations.putAll(KeyProperties.LOCATION_RIDER_PREFIX + riderId, riderInfo);
        return Response.success();
    }

    @Override
    public Response getById(Long id) {
        return Response.of(userMapper.selectById(id));
    }

    @Override
    public Response selectPageUsers(Integer currentPage, Integer pageSize) {
        Page<User> page = new Page<>(currentPage, pageSize);
        return Response.of(userMapper.selectPage(page, null));
    }

    @Override
    public Response selectRiders() {
        List<User> riderUsers = userMapper.selectRiders();
        if (riderUsers == null || riderUsers.size() == 0) {
            throw new BizException(ErrorCode.NO_DATA);
        }
        List<QueryRiderDto> riders = new ArrayList<>();
        riderUsers.forEach(riderUser -> {
            QueryRiderDto rider = new QueryRiderDto();
            BeanUtils.copyProperties(riderUser, rider);
            // 计算订单完成率
            if (riderUser.getTotalOrderQuantity() == 0) {
                rider.setOrderCompletionRate(0);
            } else {
                int rate = CommonUtil.getPercentage(riderUser.getOrderFulfillment(), riderUser.getTotalOrderQuantity());
                rider.setOrderCompletionRate(rate);
            }
            // 查询在线状态
            String statusStr = stringRedisTemplate.opsForValue().get(KeyProperties.RIDER_STATUS_PREFIX + rider.getId());
            Boolean available = statusStr == null || "0".equals(statusStr);
            rider.setAvailable(available);
            riders.add(rider);
        });
        return Response.of(riders);
    }

    @Override
    public Response updateUserById(UpdateUserCmd updateUserCmd) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, updateUserCmd.getId());
        if (!userMapper.exists(wrapper)) {
            throw new BizException(ErrorCode.ID_MISS);
        }
        User user = new User();
        BeanUtils.copyProperties(updateUserCmd, user);
        user.setUpdateTime(new Date());
        int num = userMapper.updateById(user);
        return num == 1 ? Response.success() : Response.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response addUser(AddUserCmd addUserCmd) {
        User user = new User();
        BeanUtils.copyProperties(addUserCmd, user);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BizException(ErrorCode.USERNAME_EXIST);
        }
        user.setUpdateTime(new Date());
        int num1 = userMapper.insert(user);
        int num2 = userRoleMapper.insert(new UserRole(user.getId(), authorityProperties.getRoleUser()));
        int num3 = userRoleMapper.insert(new UserRole(user.getId(), authorityProperties.getRoleRider()));
        Long userId = user.getId();
        Map<String, Long> res = new HashMap<String, Long>() {{
            put("id", userId);
        }};
        return num1 == 1 && num2 == 1 && num3 == 1 ? Response.of(res) : Response.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserForShopkeeper(User user, Long shopkeeperId) {
        user.setShopkeeperId(shopkeeperId);
        user.setUpdateTime(new Date());
        int num1 = userMapper.insert(user);
        int num2 = userRoleMapper.insert(new UserRole(user.getId(), authorityProperties.getRoleShopkeeper()));
        return num1 == 1 && num2 == 1;
    }

    @Override
    public Response getByOpenid(String openid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid, openid);
        User user = userMapper.selectOne(wrapper);
        return Response.of(user);
    }

    @Override
    public Response wxLogin(String code, AddUserCmd addUserCmd) {
        JSONObject sessionInfo = null;
        try {
            sessionInfo = JSONObject.parseObject(code2Session(code));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (sessionInfo == null) {
            throw new BizException(ErrorCode.LOGIN_ERROR);
        }
        if (sessionInfo.getInteger("errcode") != null) {
            throw new BizException(ErrorCode.LOGIN_ERROR.getErrCode(), sessionInfo.getString("errmsg"));
        }

        // 获取用户唯一标识符 openid成功，登录成功
        String openid = sessionInfo.getString("openid");
        User user = (User) getByOpenid(openid).getData();
        if (user == null) {
            //加一步注册
            addUserCmd.setOpenid(openid);
            Map<String, Long> registerRes = (Map<String, Long>) addUser(addUserCmd).getData();
            Long userId = registerRes.get("id");
            user = new User();
            BeanUtils.copyProperties(addUserCmd, user);
            user.setId(userId);
        }
        return Response.of(getLoginResponse(openid, user));
    }

    @Override
    public Response adminLogin(AdminLoginQry adminLoginQry) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, adminLoginQry.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user == null || !passwordEncoder.matches(adminLoginQry.getPassword(), user.getPassword())) {
            throw new BizException(ErrorCode.LOGIN_ERROR);
        }
        return Response.of(getLoginResponse(null, user));
    }

    @Override
    public Response getOrdersByUserId(Long userId, Integer currentPage, Integer pageSize) {
        Page<Order> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        orderMapper.selectPage(page, wrapper);
        return Response.of(page);
    }

    @Override
    public Response getOrdersByRiderId(Long riderId, Integer currentPage, Integer pageSize) {
        //从UserRole表中查询出骑手的角色id
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, riderId)
                .eq(UserRole::getRoleId, authorityProperties.getRoleRider());
        if (!userRoleMapper.exists(wrapper)) {
            throw new BizException(ErrorCode.Rider_NOT_EXIST);
        }
        //从Order表中查询出骑手的订单
        Page<Order> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Order> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Order::getRiderId, riderId);
        orderMapper.selectPage(page, wrapper1);
        return Response.of(page);
    }


    /**
     * 登录凭证校验
     *
     * @param code
     * @return
     * @throws Exception
     */
    private String code2Session(String code) throws Exception {
        String sessionInfo = WxCode2SessionUtil.jsCode2session(weChatProperties.getAppid(),
                weChatProperties.getSecret(), code, weChatProperties.getGrantType());
        return sessionInfo;
    }

    @NotNull
    private Map<String, Object> getLoginResponse(String openid, User user) {
        Long userId = user.getId();
        //查询权限信息
        List<String> roles = userMapper.getRoles(userId);
        List<String> authorities = userMapper.getAuthorities(userId);
        UserPermission userPermission = new UserPermission();
        userPermission.setUserId(String.valueOf(userId));
        userPermission.setOpenid(openid);
        userPermission.setRoles(roles);
        userPermission.setAuthorities(authorities);
        //存入redis
        String key = KeyProperties.TOKEN_PREFIX + userId;
        String value = JSON.toJSONString(userPermission);
        stringRedisTemplate.opsForValue()
                .set(key, value, configProperties.getRefreshTokenExpiration(), TimeUnit.MILLISECONDS);
        stringRedisTemplate.opsForValue().set(key, value);
        //根据userId生成jwt
        String accessToken = JwtUtil.createJWT(String.valueOf(userId), configProperties.getAccessTokenExpiration());
        String refreshToken = JwtUtil.createJWT(String.valueOf(userId), configProperties.getRefreshTokenExpiration());
        //封装jwt为token返回
        Map<String, Object> resp = new HashMap<>();
        resp.put(ACCESS_TOKEN, accessToken);
        resp.put(REFRESH_TOKEN, refreshToken);
        resp.put(USERNAME, user.getUsername());
        resp.put(USER_ID, userId);
        return resp;
    }

    @Override
    public Response updateRiderStatus(Long id) {
        return userMapper.updateRiderStatus(id) == 1 ? Response.success() : Response.error();
    }

    @Override
    public Response clearRiderRemaining(ClearRiderRemainingCmd clearRiderRemainingCmd) {
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(User::getUsername, clearRiderRemainingCmd.getUsername());
        User user = userMapper.selectOne(wrapper1);
        if (user == null || !passwordEncoder.matches(clearRiderRemainingCmd.getPassword(), user.getPassword())) {
            throw new BizException(ErrorCode.PASSWORD_ERROR);
        }
        LambdaQueryWrapper<User> wrapper2 = new LambdaQueryWrapper<>();
        Long id = clearRiderRemainingCmd.getId();
        wrapper2.eq(User::getId, id);
        if (!userMapper.exists(wrapper2)) {
            throw new BizException(ErrorCode.Rider_NOT_EXIST);
        }
        String code = stringRedisTemplate.opsForValue().get(KeyProperties.TEMPORARY_WITHDRAWAL_CODE_PREFIX + id);
        if (!clearRiderRemainingCmd.getTemporaryWithdrawalCode().equals(code)) {
            throw new BizException(ErrorCode.TEMPORARY_WITHDRAWAL_COED_ERROR);
        }
        //记录骑手余额明细
        RiderRemainingDetail riderRemainingDetail = new RiderRemainingDetail();
        Double money = userMapper.selectById(id).getRiderRemaining()*(-1);
        riderRemainingDetail.setCreateTime(new Date());
        riderRemainingDetail.setType(2);
        riderRemainingDetail.setRiderId(id);
        riderRemainingDetail.setMoney(money);
        riderRemainingDetail.setRemaining(0.0);
        riderRemainingDetailMapper.insert(riderRemainingDetail);
        return userMapper.clearRiderRemaining(id) > 0 ? Response.success() : Response.error();
    }

    @Override
    public Response updateRiderRemaining(Long id, Double riderRemaining) {
        return userMapper.updateRiderRemaining(id, riderRemaining) > 0 ? Response.success() : Response.error();
    }

    @Override
    public Response addUserRole(AddUserRoleCmd addUserRoleCmd) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(addUserRoleCmd, userRole);
        return userRoleMapper.insert(userRole) == 1 ? Response.success() : Response.error();
    }

    @Override
    public Response deleteUserRole(Long userId, Integer roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        wrapper.eq(UserRole::getRoleId, roleId);
        return userRoleMapper.delete(wrapper) == 1 ? Response.success() : Response.error();
    }

    @Override
    public Response getAllUserRole(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        return Response.of(userRoleMapper.selectList(wrapper));
    }

    @Override
    public Response refreshToken(String refreshToken) {
        //解析token
        String userId = "";
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(refreshToken);
        } catch (Exception e) {
            throw new BizException(ErrorCode.TOKEN_PARSE_ERROR);
        }
        userId = claims.getSubject();
        //从redis获取用户信息
        String key = KeyProperties.TOKEN_PREFIX + userId;
        String userInfo = stringRedisTemplate.opsForValue().get(key);
        //重新设置redis Key
        stringRedisTemplate.delete(key);
        stringRedisTemplate.opsForValue()
                .set(key, userInfo, configProperties.getRefreshTokenExpiration(), TimeUnit.MILLISECONDS);
        //生成新的token
        //根据userId生成jwt
        String accessToken = JwtUtil.createJWT(String.valueOf(userId), configProperties.getAccessTokenExpiration());
        refreshToken = JwtUtil.createJWT(String.valueOf(userId), configProperties.getRefreshTokenExpiration());
        //封装jwt为token返回
        Map<String, String> resp = new HashMap<>(2);
        resp.put(ACCESS_TOKEN, accessToken);
        resp.put(REFRESH_TOKEN, refreshToken);
        return Response.of(resp);
    }

    @Override
    public Response getTemporaryWithdrawalCode() {
        String code = RandomUtil.randomNumbers(4);
        Long id = JwtAuthenticationTokenFilter.threadLocal.get();
        stringRedisTemplate.opsForValue().set(KeyProperties.TEMPORARY_WITHDRAWAL_CODE_PREFIX + id, code, 1, TimeUnit.MINUTES);
        return Response.of(code);
    }
}




