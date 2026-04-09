package com.neu.deliveryPlatform.config.permission.handler;

import com.alibaba.fastjson.JSON;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.util.Response;
import com.neu.deliveryPlatform.util.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author asiawu
 * @Date 2023-04-07 21:25
 * @Description: 用户未登录时访问需要jwt接口时
 */

//处理认证异常
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Response result = Response.error(ErrorCode.NO_SIGN_IN);
        String res = JSON.toJSONString(result);
        WebUtils.renderString(response, res);
    }
}
