package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.service.RiderRemainingDetailService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zed
 * @Date 2023/6/20 19:15
 * @Description:
 */
@Api(tags = "骑手余额明细")
@RestController
@RequestMapping("/api/RiderRemainingDetail")
public class RiderRemainingDetailController {

    @Autowired
    RiderRemainingDetailService riderRemainingDetailService;

    @ApiOperation(value = "根据骑手Id查询余额明细", notes = "传入骑手Id，按时间降序返回该骑手余额明细")
    @GetMapping("/getDetailsById")
    public Response getDetailsById(@RequestParam Long riderId,@RequestParam int currentPage, @RequestParam int pageSize) {
        return riderRemainingDetailService.getDetailsById(riderId,currentPage,pageSize);
    }

    @ApiOperation(value = "查询所有余额明细", notes = "传入type，null或者0查询所有，1查询收入记录，2查询提现记录")
    @GetMapping("/getAllDetails")
    public Response getAllDetails(@RequestParam Integer type,@RequestParam int currentPage, @RequestParam int pageSize) {
        return riderRemainingDetailService.getAllDetails(type,currentPage,pageSize);
    }
}
