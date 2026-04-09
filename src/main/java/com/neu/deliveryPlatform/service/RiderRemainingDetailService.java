package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.util.Response;

/**
 * @Author Zed
 * @Date 2023/6/20 19:09
 * @Description: 针对表【(骑手余额明细表)】的数据库操作Service
 */
public interface RiderRemainingDetailService {
    Response getDetailsById(Long riderId,int currentPage, int pageSize);

    Response getAllDetails(Integer type,int currentPage, int pageSize);
}
