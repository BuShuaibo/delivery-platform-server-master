package com.neu.deliveryPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.deliveryPlatform.entity.RiderRemainingDetail;
import com.neu.deliveryPlatform.mapper.RiderRemainingDetailMapper;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.service.RiderRemainingDetailService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Zed
 * @Date 2023/6/20 19:10
 * @Description: 针对表【(骑手余额明细表)】的数据库操作Service
 */
@Service
public class RiderRemainingDetailServiceImpl implements RiderRemainingDetailService {

    @Autowired
    RiderRemainingDetailMapper riderRemainingDetailMapper;

    @Override
    public Response getDetailsById(Long riderId, int currentPage, int pageSize) {
        LambdaQueryWrapper<RiderRemainingDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RiderRemainingDetail::getRiderId, riderId).orderByDesc(RiderRemainingDetail::getCreateTime);
        Page<RiderRemainingDetail> page = new Page<>(currentPage, pageSize);
        riderRemainingDetailMapper.selectPage(page, wrapper);
        return Response.of(page);
    }

    @Override
    public Response getAllDetails(Integer type,int currentPage, int pageSize){
        Page<RiderRemainingDetail> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<RiderRemainingDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(RiderRemainingDetail::getCreateTime);
        if (type==1) {
            wrapper.eq(RiderRemainingDetail::getType,1);
        } else if (type==2) {
            wrapper.eq(RiderRemainingDetail::getType,2);
        } else if (type==null||type==0){
            riderRemainingDetailMapper.selectPage(page, wrapper);
            return Response.of(page);
        }
        if (type==1||type==2) {
            riderRemainingDetailMapper.selectPage(page, wrapper);
            return Response.of(page);
        } else {
            return Response.error(ErrorCode.PARAM_ERROR);
        }
    }

}

















