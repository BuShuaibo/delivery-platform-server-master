package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.Commodity;
import com.neu.deliveryPlatform.entity.ShopkeeperCommodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Asia
* @description 针对表【shopkeeper_commodity(商家-商品中间表)】的数据库操作Mapper
* @createDate 2023-04-05 17:25:22
* @Entity com.neu.deliveryPlatform.entity.ShopkeeperCommodity
*/
@Mapper
public interface ShopkeeperCommodityMapper extends BaseMapper<ShopkeeperCommodity> {

    List<Commodity> getShopkeeperCommodity(@Param("id") Long id,@Param("current") int current,@Param("size") int size);

}




