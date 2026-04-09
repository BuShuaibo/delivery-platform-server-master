package com.neu.deliveryPlatform.strategy.impl;

import com.neu.deliveryPlatform.entity.Order;
import com.neu.deliveryPlatform.strategy.OrderAsynchronousProcessingStrategy;
import org.springframework.stereotype.Component;

/**
 * @Author asiawu
 * @Date 2023-05-16 9:46
 * @Description:
 */
@Component("mq")
public class MqStrategy implements OrderAsynchronousProcessingStrategy {

    @Override
    public void execute(Order order) {

    }
}
