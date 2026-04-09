package com.neu.deliveryPlatform.service.impl;

import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.dto.cmd.AddPlaceCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdatePlaceCmd;
import com.neu.deliveryPlatform.entity.Location;
import com.neu.deliveryPlatform.entity.Place;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.repository.PlaceRepository;
import com.neu.deliveryPlatform.service.PlaceService;
import com.neu.deliveryPlatform.util.ObjectUtils;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @Author asiawu
 * @Date 2023-04-18 17:50
 * @Description:
 */
@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;

    @Override
    public Response updatePlace(UpdatePlaceCmd updatePlaceCmd) {
        //防止null覆盖原数据
        Optional<Place> optional = placeRepository.findById(updatePlaceCmd.getId());
        optional.orElseThrow((Supplier<RuntimeException>) () -> new BizException(ErrorCode.ID_MISS));
        Place oldPlaceInfo = optional.get();

        Place place = new Place();
        Location location = new Location(updatePlaceCmd.getLongitude(), updatePlaceCmd.getLatitude());
        BeanUtils.copyProperties(updatePlaceCmd, place);
        place.setLocation(location);

        //防止null覆盖原数据
        try {
            ObjectUtils.copyToNullProperties(oldPlaceInfo, place);
            ObjectUtils.copyToNullProperties(oldPlaceInfo.getLocation(), location);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        placeRepository.save(place);
        return Response.success();
    }

    @Override
    public Response addPlace(AddPlaceCmd addPlaceCmd) {
        Location location = new Location(addPlaceCmd.getLongitude(), addPlaceCmd.getLatitude());
        Place place = new Place();
        place.setName(addPlaceCmd.getName());
        place.setLocation(location);
        placeRepository.save(place);
        return Response.success();
    }

    @Override
    public Response deleteById(String id) {
        Optional<Place> optional = placeRepository.findById(id);
        optional.orElseThrow((Supplier<RuntimeException>) () -> new BizException(ErrorCode.ID_MISS));
        placeRepository.deleteById(id);
        return Response.success();
    }

    @Override
    public Response getAll() {
        return Response.of(placeRepository.findAll());
    }

    @Override
    public Response getById(String id) {
        Optional<Place> optional = placeRepository.findById(id);
        optional.orElseThrow((Supplier<RuntimeException>) () -> new BizException(ErrorCode.ID_MISS));
        return Response.of(optional.get());
    }
}
