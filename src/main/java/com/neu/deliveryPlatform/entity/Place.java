package com.neu.deliveryPlatform.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @Author asiawu
 * @Date 2023-04-18 18:16
 * @Description:
 */
@Document("place")
@Data
public class Place {
    /**
     * 地点id
     */
    @MongoId
    private String id;

    /**
     * 地点名字
     */
    private String name;

    /**
     * 地点位置
     */
    private Location location;
}



