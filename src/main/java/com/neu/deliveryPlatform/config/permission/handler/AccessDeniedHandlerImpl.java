package com.neu.deliveryPlatform.config.permission.handler;

import com.alibaba.fastjson.JSON;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.util.Response;
import com.neu.deliveryPlatform.util.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author asiawu
 * @Date 2023-04-08 1:11
 * @Description: 用户权限不足时返回错误信息
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Response resp = Response.error(ErrorCode.AUTH_ERROR);
        String res = JSON.toJSONString(resp);
        WebUtils.renderString(response, res);
    }
}
