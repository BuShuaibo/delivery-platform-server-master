package com.neu.deliveryPlatform.strategy;

import com.neu.deliveryPlatform.entity.Order;

/**
 * @Author asiawu
 * @Date 2023-05-16 9:34
 * @Description: 异步分配报酬策略(用线程池还是mq)
 */
public interface OrderAsynchronousProcessingStrategy {
    void execute(Order order);
}
