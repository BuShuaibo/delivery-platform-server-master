package com.neu.deliveryPlatform.config.permission.expression;

import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.entity.UserPermission;
import com.neu.deliveryPlatform.properties.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author asiawu
 * @Date 2023-04-09 10:39
 * @Description: 基于角色的自定义鉴权  鉴权时调用
 */
@Component("rbacExpressionRoot")
public class RbacExpressionRoot {
    public boolean hasAuthority(String authority) {
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BizException(ErrorCode.NO_SIGN_IN);
        }
        Object loginUser = authentication.getPrincipal();
        if (loginUser instanceof UserPermission) {
            return ((UserPermission) loginUser).getAuthorities().contains(authority);
        } else {
            return false;
        }
    }

    public boolean hasRole(String role) {
        //获取当前用户角色
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BizException(ErrorCode.NO_SIGN_IN);
        }
        Object loginUser = authentication.getPrincipal();
        if (loginUser instanceof UserPermission) {
            return ((UserPermission) loginUser).getRoles().contains(role);
        } else {
            return false;
        }
    }
}
