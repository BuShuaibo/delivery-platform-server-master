package com.neu.deliveryPlatform.strategy;

import com.neu.deliveryPlatform.config.GlobalExceptionHandlerConfig;
import com.neu.deliveryPlatform.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author asiawu
 * @Date 2023-05-16 10:46
 * @Description:
 */
@Component
public class OrderAsynchronousProcessingFactory {
    @Autowired
    Map<String, OrderAsynchronousProcessingStrategy> orderAsynchronousProcessingStrategyMap;
    @Value("${remunerationSharingMode}")
    private String orderAsynchronousProcessingMode;

    public void execute(Order order) {
        try {
            orderAsynchronousProcessingStrategyMap.get(orderAsynchronousProcessingMode).execute(order);
        } catch (Exception e) {
            GlobalExceptionHandlerConfig.log.error("订单异步处理出错，订单信息：{}", order);
        }
    }
}
