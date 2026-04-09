package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.entity.Spider;
import com.neu.deliveryPlatform.util.Response;

import java.text.ParseException;

/**
 * @author laobuzhang
 * @description 用来跟新爬虫时间
 * @createDate 2023-06-19 21:35
 */
public interface SpiderService {
    Response updateCnt(String date,Integer endNumber) throws ParseException;
    String getCnt();
}
