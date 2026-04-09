package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.config.WebSocket;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author asiawu
 * @Date 2023-04-10 19:08
 * @Description:
 */
@Api(tags = "聊天(待开发)")
@RestController
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    WebSocket webSocket;
//    @PostMapping("/jdt")
//    public void jdt() throws InterruptedException {
//        String msg="";
//        int a=0;
//        for (int i = 0; i <=100; i++) {
//            msg=String.valueOf(a);
//            Thread.sleep(100);
//            webSocket.sendMessage(msg);
//            a++;
//        }
//    }
}
