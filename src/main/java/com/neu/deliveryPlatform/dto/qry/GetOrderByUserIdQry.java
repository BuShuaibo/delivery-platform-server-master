package com.neu.deliveryPlatform.dto.qry;

import lombok.Data;

/**
 * @Author： JiuYou
 * @Date： 2023/4/19
 * @Description：
 */
@Data
public class GetOrderByUserIdQry {
    private Long userId;
    private Integer currentPage;
    private Integer pageSize;
}
