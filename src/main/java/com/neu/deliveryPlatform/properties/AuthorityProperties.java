package com.neu.deliveryPlatform.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author： JiuYou
 * @Date： 2023/4/9
 * @Description：
 */
@ConfigurationProperties(prefix = "buxiwan.authority")
@Component
@Data
public class AuthorityProperties {
    private Integer roleAdmin;
    private Integer roleUser;
    private Integer roleRider;
    private Integer roleShopkeeper;
}
