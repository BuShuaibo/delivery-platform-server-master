package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.AddPlaceCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdatePlaceCmd;
import com.neu.deliveryPlatform.service.PlaceService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author asiawu
 * @Date 2023-04-18 13:14
 * @Description:
 */
@Api(tags = "常用地点")
@RestController
@RequestMapping("/api/place")
public class PlaceController {
    @Autowired
    PlaceService placeService;

    @ApiOperation(value = "修改常用地点信息", notes = "根据id更新常用地点信息")
    @PostMapping("/update")
    public Response updatePlace(@Valid @RequestBody UpdatePlaceCmd updatePlaceCmd) {
        return placeService.updatePlace(updatePlaceCmd);
    }

    @ApiOperation(value = "新增常用地点", notes = "新增常用地点")
    @PostMapping("/add")
    public Response addPlace(@Valid @RequestBody AddPlaceCmd addPlaceCmd) {
        return placeService.addPlace(addPlaceCmd);
    }

    @ApiOperation(value = "删除常用地点", notes = "根据id删除常用地点")
    @GetMapping("/deleteById")
    public Response deleteById(@RequestParam String id) {
        return placeService.deleteById(id);
    }

    @ApiOperation(value = "查询所有常用地点", notes = "查询所有常用地点")
    @GetMapping("/getAll")
    public Response getAll() {
        return placeService.getAll();
    }

    @ApiOperation(value = "根据id查询地点", notes = "根据id查询地点信息")
    @GetMapping("/getById")
    public Response getById(@RequestParam String id) {
        return placeService.getById(id);
    }
}
