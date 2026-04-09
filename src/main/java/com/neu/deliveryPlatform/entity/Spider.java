package com.neu.deliveryPlatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 爬虫时间表
 * @TableName spider_last_time
 */
@TableName(value = "sys_spider_last_time")
@Data
public class Spider {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Date date;
    private Integer endNumber;
}
