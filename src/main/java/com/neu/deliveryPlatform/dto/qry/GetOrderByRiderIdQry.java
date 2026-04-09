package com.neu.deliveryPlatform.dto.qry;

import lombok.Data;

/**
 * @Author： JiuYou
 * @Date： 2023/4/19
 * @Description：
 */
@Data
public class GetOrderByRiderIdQry {
    private Long riderId;
    private Integer currentPage;
    private Integer pageSize;
}
