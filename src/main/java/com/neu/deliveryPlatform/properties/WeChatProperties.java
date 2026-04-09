package com.neu.deliveryPlatform.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author asiawu
 * @Date 2023-04-09 11:10
 * @Description:
 */
@ConfigurationProperties(prefix = "wechat")
@Component
@Data
public class WeChatProperties {
    private String appid;
    private String secret;
    private String grantType;
}
