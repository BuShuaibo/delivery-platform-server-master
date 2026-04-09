package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.OrderShopkeeper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Asia
* @description 针对表【order_shopkeeper(订单-商家中间表)】的数据库操作Mapper
* @createDate 2023-04-05 17:25:22
* @Entity com.neu.deliveryPlatform.entity.OrderShopkeeper
*/
@Mapper
public interface OrderShopkeeperMapper extends BaseMapper<OrderShopkeeper> {

}




