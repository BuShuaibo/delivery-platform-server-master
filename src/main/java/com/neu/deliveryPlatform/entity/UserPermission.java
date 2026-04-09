package com.neu.deliveryPlatform.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author asiawu
 * @Date 2023-04-09 20:11
 * @Description:
 */
@Data
public class UserPermission {
    private String userId;
    private String openid;
    private List<String> roles;
    private List<String> authorities;
}
