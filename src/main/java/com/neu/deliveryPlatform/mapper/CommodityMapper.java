package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Asia
 * @description 针对表【commodity(商品表)】的数据库操作Mapper
 * @createDate 2023-04-05 17:25:22
 * @Entity com.neu.deliveryPlatform.entity.Commodity
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

    List<Commodity> getAllBrief();

    int InsertOrUpdate(Commodity commodity);

    Long getCategoryIdByName(@Param("categoryName") String categoryName);

}




