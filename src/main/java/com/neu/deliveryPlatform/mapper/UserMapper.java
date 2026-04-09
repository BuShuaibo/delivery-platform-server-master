package com.neu.deliveryPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.deliveryPlatform.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Asia
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2023-04-05 17:25:22
 * @Entity com.neu.deliveryPlatform.entity.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<String> getAuthorities(Long id);

    List<String> getRoles(Long id);

    List<User> selectRiders();

    int updateRiderStatus(Long id);

    int clearRiderRemaining(Long id);

    int updateRiderRemaining(Long id, Double riderRemaining);

    void incrRiderTotalOrderQuantity(Long riderId);

    void incrRiderOrderFulfillment(Long riderId);
}




