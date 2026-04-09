package com.neu.deliveryPlatform.service.impl;

import com.neu.deliveryPlatform.dto.cmd.UpdateLocationCmd;
import com.neu.deliveryPlatform.entity.Location;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.properties.KeyProperties;
import com.neu.deliveryPlatform.repository.PlaceRepository;
import com.neu.deliveryPlatform.service.LocationService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author asiawu
 * @Date 2023-04-19 8:16
 * @Description:
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PlaceRepository placeRepository;



    @Override
    public Response updateLocation(UpdateLocationCmd updateLocationCmd) {
        String key = KeyProperties.LOCATION_ORDER_PREFIX + updateLocationCmd.getOrderId();
        if (!redisTemplate.hasKey(key)) {
            return Response.error(ErrorCode.NO_DATA);
        } else {
            Location riderLocation = new Location();
            riderLocation.setLatitude(updateLocationCmd.getLatitude());
            riderLocation.setLongitude(updateLocationCmd.getLongitude());
            redisTemplate.opsForHash().put(key,KeyProperties.LOCATION_RIDER_KEY,riderLocation);
            return Response.success();
        }
    }
}
