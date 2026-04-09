package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.util.Response;

/**
 * @Author asiawu
 * @Date 2023-04-09 11:09
 * @Description:
 */
public interface WeChatService {
    /**
     * 小程序登录
     *
     * @param code
     * @return
     */
    Response wxLogin(String code) throws Exception;
}
