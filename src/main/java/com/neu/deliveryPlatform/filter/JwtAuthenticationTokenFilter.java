package com.neu.deliveryPlatform.filter;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.entity.UserPermission;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.properties.KeyProperties;
import com.neu.deliveryPlatform.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author asiawu
 * @Date 2023-04-08 0:59
 * @Description: 放在所有过滤器前面，检查浏览器携带的token是否合法
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String accessToken = request.getHeader(KeyProperties.TOKEN_HEADER);
        if (StringUtils.isEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            //加return为了返回时第二次经过过滤器不再执行下面的代码
            return;
        }
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        //解析token
        String userId = "";
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(accessToken);
        } catch (Exception e) {
            throw new BizException(ErrorCode.TOKEN_PARSE_ERROR);
        }
        userId = claims.getSubject();
        //从redis获取用户信息
        String key = KeyProperties.TOKEN_PREFIX + userId;
        String userInfo = stringRedisTemplate.opsForValue().get(key);
        UserPermission userPermission = JSONObject.parseObject(userInfo, UserPermission.class);
        if (userPermission == null) {
            throw new BizException(ErrorCode.TOKEN_PARSE_ERROR);
        }
        //存入SecurityContextHolder
        // 获取权限信息封装到authticationToken中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userPermission, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //存入ThreadLocal
        threadLocal.set(Long.valueOf(userId));
        //放行
        filterChain.doFilter(request, response);
    }
}
