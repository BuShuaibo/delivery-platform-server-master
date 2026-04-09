package com.neu.deliveryPlatform.properties;

/**
 * @Author asiawu
 * @Date 2023-03-19 10:54
 * @Description:
 */
public interface BaseErrorInfoInterface {
    /**
     *  错误码
     */
    String getErrCode();

    /**
     * 错误描述
     */
    String getErrDesc();
}
