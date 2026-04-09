package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.dto.cmd.AddPlaceCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdatePlaceCmd;
import com.neu.deliveryPlatform.util.Response;

/**
 * @Author asiawu
 * @Date 2023-04-18 17:50
 * @Description:
 */
public interface PlaceService {
    Response updatePlace(UpdatePlaceCmd updatePlaceCmd);

    Response addPlace(AddPlaceCmd addPlaceCmd);

    Response deleteById(String id);

    Response getAll();

    Response getById(String id);
}
