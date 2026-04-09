package com.neu.deliveryPlatform.service;


import com.neu.deliveryPlatform.dto.cmd.AddOrderCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateOrderCmd;
import com.neu.deliveryPlatform.util.Response;


/**
* @author Asia
* @description 针对表【order(订单表)】的数据库操作Service
* @createDate 2023-04-05 17:25:22
*/
public interface OrderService {
    Response addOrder(AddOrderCmd addOrderCmd);

    Response updateOrder(UpdateOrderCmd updateOrderCmd);

    Response getOrderById(Long id);

    Response getAllOrders(int nowPage,int perPage);

    Response getByStatus(int currentPage, int pageSize, int status);

    Response getUndoneOrders(Long riderId);

    Response getDoneOrders(int currentPage, int pageSize, Long riderId);
}
