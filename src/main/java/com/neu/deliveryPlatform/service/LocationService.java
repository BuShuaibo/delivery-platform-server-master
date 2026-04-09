package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.dto.cmd.UpdateLocationCmd;
import com.neu.deliveryPlatform.util.Response;

/**
 * @Author asiawu
 * @Date 2023-04-19 8:15
 * @Description:
 */
public interface LocationService {
    public Response updateLocation(UpdateLocationCmd updateLocationCmd);
}
