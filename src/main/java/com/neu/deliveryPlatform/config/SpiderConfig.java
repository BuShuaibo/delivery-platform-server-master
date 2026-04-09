package com.neu.deliveryPlatform.config;

import com.neu.deliveryPlatform.util.SpiderUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
@EnableScheduling
public class SpiderConfig {

    private final SpiderUtil spiderUtil;
    public SpiderConfig(SpiderUtil spiderUtil){
        this.spiderUtil=spiderUtil;
    }

    @Scheduled(fixedDelayString = "${spider.schedule.delay}")
    public void scheduleTask() {
        try {
            spiderUtil.spider();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
