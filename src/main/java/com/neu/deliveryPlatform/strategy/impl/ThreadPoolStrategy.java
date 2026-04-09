package com.neu.deliveryPlatform.strategy.impl;

import com.neu.deliveryPlatform.config.GlobalExceptionHandlerConfig;
import com.neu.deliveryPlatform.entity.Order;
import com.neu.deliveryPlatform.entity.RiderRemainingDetail;
import com.neu.deliveryPlatform.entity.User;
import com.neu.deliveryPlatform.mapper.RiderRemainingDetailMapper;
import com.neu.deliveryPlatform.mapper.UserMapper;
import com.neu.deliveryPlatform.properties.KeyProperties;
import com.neu.deliveryPlatform.strategy.OrderAsynchronousProcessingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author asiawu
 * @Date 2023-05-16 9:46
 * @Description:
 */
@Component("threadPool")
public class ThreadPoolStrategy implements OrderAsynchronousProcessingStrategy {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RiderRemainingDetailMapper riderRemainingDetailMapper;

    @Override
    @Async
    public void execute(Order order) {
        try {
            //更新骑手余额
            userMapper.updateRiderRemaining(order.getRiderId(), order.getRiderRemuneration());
            //TODO 其他端怎么分配？
            //骑手完成量+1
            Long riderId = order.getRiderId();
            userMapper.incrRiderOrderFulfillment(riderId);

            // 骑手接单量-1
            stringRedisTemplate.opsForValue().decrement(KeyProperties.RIDER_STATUS_PREFIX + riderId);

            //记录骑手余额明细
            User rider = userMapper.selectById(riderId);
            RiderRemainingDetail riderRemainingDetail = new RiderRemainingDetail();
            riderRemainingDetail.setCreateTime(new Date());
            riderRemainingDetail.setType(1);
            riderRemainingDetail.setRiderId(riderId);
            riderRemainingDetail.setMoney(order.getRiderRemuneration());
            riderRemainingDetail.setOrderId(order.getId());
            riderRemainingDetail.setRemaining(rider.getRiderRemaining());
            riderRemainingDetailMapper.insert(riderRemainingDetail);
        } catch (Exception e) {
            GlobalExceptionHandlerConfig.log.error("订单报酬分配出错，订单信息：{}", order);
        }
    }
}
