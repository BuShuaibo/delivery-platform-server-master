package com.neu.deliveryPlatform.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author asiawu
 * @Date 2023-03-17 21:06
 * @Description: 将配置文件的信息转化为实体
 */
@ConfigurationProperties(prefix = "buxiwan")
@Component
@Data
public class ConfigProperties {
    private String[] nonFilterPath;

    private String[] jwtFilterPath;

    private String[] staticSource;

    private long accessTokenExpiration;
    private long refreshTokenExpiration;
}
