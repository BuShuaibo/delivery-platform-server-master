package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.AddShopkeeperCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateShopkeeperCmd;
import com.neu.deliveryPlatform.dto.qry.GetShopkeeperCommodityCmd;
import com.neu.deliveryPlatform.service.ShopkeeperService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author asiawu
 * @Date 2023-04-05 15:15
 * @Description:
 */

@Api(tags = "商家")
@RestController
@RequestMapping("/api/shopkeeper")
public class ShopkeeperController {
    @Resource
    ShopkeeperService shopkeeperService;

    @ApiOperation(value = "根据id查询商家", notes = "根据id查询商家具体信息(不包含商家的商品)")
    @GetMapping("/getById")
    public Response getById(@RequestParam Long id) {
        return shopkeeperService.getShopkeeperById(id);
    }

    @ApiOperation(value = "分页查询所有商家", notes = "如果想查询所有商家，请传入-1、-1")
    @GetMapping("/getAll")
    public Response getShopkeepers(@RequestParam int currentPage, @RequestParam int pageSize) {
        return shopkeeperService.getShopkeepers(currentPage, pageSize);
    }

    @ApiOperation(value = "新增商家",
            notes = "新增商家，不仅需要传入商家表中的部分字段，还需要传入该商家所对应的小程序用户端用户相关的数据，返回商家id")
    @PostMapping("/add")
    public Response add(@Valid @RequestBody AddShopkeeperCmd addShopkeeperCmd) {
        return shopkeeperService.addShopkeeper(addShopkeeperCmd);
    }

    @ApiOperation(value = "删除商家", notes = "通过id删除商家，同时会删除商家对应的用户")
    @PostMapping("/delete")
    public Response delete(@RequestParam Long id) {
        return shopkeeperService.deleteShopkeeper(id);
    }

    @ApiOperation(value = "更新商家", notes = "通过id更新商家信息")
    @PostMapping("/updateById")
    public Response update(@Valid @RequestBody UpdateShopkeeperCmd updateShopkeeperCmd) {
        return shopkeeperService.updateShopkeeper(updateShopkeeperCmd);
    }

    @ApiOperation(value = "分页查询商品", notes = "分页查询商家的所有商品,页码从1开始")
    @GetMapping("/getShopkeeperCommodity")
    public Response getShopkeeperCommodity(@Valid @RequestBody GetShopkeeperCommodityCmd getShopkeeperCommodityCmd) {
        return shopkeeperService.getShopkeeperCommodity(getShopkeeperCommodityCmd);
    }

    @ApiOperation(value = "查询商家商品数量", notes = "通过id查询商家的商品数量")
    @GetMapping("/getCommodityCount")
    public Response getCommodityCount(@RequestParam Long id) {
        return shopkeeperService.getCommodityCount(id);
    }

    @ApiOperation(value = "根据商家查询对应用户id", notes = "通过商家id找到对应的用户id")
    @GetMapping("/getUserId")
    public Response getUserIdByShopkeeperId(@RequestParam Long id) {
        return shopkeeperService.getUserIdByShopkeeperId(id);
    }
}
