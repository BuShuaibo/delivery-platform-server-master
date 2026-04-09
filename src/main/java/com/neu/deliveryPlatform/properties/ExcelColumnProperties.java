package com.neu.deliveryPlatform.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author asiawu
 * @Date 2023-05-21 23:05
 * @Description:
 */
@ConfigurationProperties(prefix = "excel-column")
@Component
@Data
public class ExcelColumnProperties {
    private String name;
    private String description;
    private String price;
    private String sales;
    private String id;
    private String categoryId;

}
