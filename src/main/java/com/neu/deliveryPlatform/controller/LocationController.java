package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.UpdateLocationCmd;
import com.neu.deliveryPlatform.service.LocationService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author asiawu
 * @Date 2023-04-19 8:06
 * @Description:
 */
@Api(tags = "位置")
@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    LocationService locationService;


    @ApiOperation(value = "位置改变(轮询)",
            notes = "骑手接单后每隔一段时间向后端发送一次该请求，用于记录骑手位置及计算骑手走的总路程")
    @PostMapping("/update")
    public Response updateLocation(@Valid @RequestBody UpdateLocationCmd updateLocationCmd) {
        return locationService.updateLocation(updateLocationCmd);
    }
}
