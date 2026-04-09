package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Asia
* @description 针对表【order(订单表)】的数据库操作Mapper
* @createDate 2023-04-05 17:25:22
* @Entity com.neu.deliveryPlatform.entity.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




