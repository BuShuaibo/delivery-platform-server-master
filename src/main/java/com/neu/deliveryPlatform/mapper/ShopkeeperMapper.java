package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.Shopkeeper;
import com.neu.deliveryPlatform.util.Response;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Asia
* @description 针对表【shopkeeper(商家表)】的数据库操作Mapper
* @createDate 2023-04-05 17:25:22
* @Entity com.neu.deliveryPlatform.entity.Shopkeeper
*/
@Mapper
public interface ShopkeeperMapper extends BaseMapper<Shopkeeper> {

    Long getUserIdByShopkeeperId(Long id);
}




