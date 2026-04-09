package com.neu.deliveryPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.deliveryPlatform.entity.Spider;
import com.neu.deliveryPlatform.mapper.SpiderMapper;
import com.neu.deliveryPlatform.service.SpiderService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SpiderServiceImpl implements SpiderService {
    @Autowired
    SpiderMapper spiderMapper;

    @Override
    public Response updateCnt(String date,Integer endNumber) throws ParseException {
        Spider spider=new Spider();
        spider.setId(new Long(1));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date updateTime = sdf.parse(date);
        spider.setDate(updateTime);

        spider.setEndNumber(endNumber);

        QueryWrapper<Spider> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",1);
        spiderMapper.update(spider,queryWrapper);

        return Response.success();
    }


    @Override
    public String getCnt() {
        Spider spider=spiderMapper.selectById(1);
        Date date=spider.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate=sdf.format(date);
        String endNumber=spider.getEndNumber().toString();
        return strDate+endNumber;
    }
}
