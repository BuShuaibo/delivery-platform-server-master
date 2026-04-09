package com.neu.deliveryPlatform.controller;


import com.neu.deliveryPlatform.entity.Spider;
import com.neu.deliveryPlatform.service.SpiderService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


/**
 * @Author laobuzhang
 * @Date 2023-06-19 21:32
 * @Description:
 */

@Api(tags = "爬虫")
@RestController
@RequestMapping("/api/spider")
public class SpiderController {

    @Autowired
    SpiderService spiderService;

    @ApiOperation(value = "更新时间戳", notes = "更新爬虫上次爬取的时间戳")
    @PostMapping("/updateCnt")
    public Response updateCnt(@RequestParam String date,@RequestParam Integer endNumber) throws ParseException {
        return spiderService.updateCnt(date,endNumber);
    }

    @ApiOperation(value = "查询时间戳", notes = "查询上次查询记录的时间戳，格式为date+换行+endNumber")
    @GetMapping("/getCnt")
    public String getCnt() {
        return spiderService.getCnt();
    }
}
