package com.neu.deliveryPlatform;

import com.neu.deliveryPlatform.mapper.CommodityMapper;
import com.neu.deliveryPlatform.mapper.UserMapper;
import com.neu.deliveryPlatform.properties.ConfigProperties;
import com.neu.deliveryPlatform.properties.ExcelColumnProperties;
import com.neu.deliveryPlatform.repository.PlaceRepository;
import com.neu.deliveryPlatform.strategy.OrderAsynchronousProcessingFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ConfigProperties configProperties;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    OrderAsynchronousProcessingFactory factory;
    @Autowired
    CommodityMapper commodityMapper;

    @Resource
    ExcelColumnProperties excelColumnProperties;

    @Test
    void contextLoads() {
    }

//    @Test
//    void testExcel() {
//        LambdaQueryWrapper<Commodity> wrapper=new LambdaQueryWrapper<>();
//        wrapper.ge(Commodity::getId,1L);
//        commodityMapper.delete(wrapper);
//    }


}
