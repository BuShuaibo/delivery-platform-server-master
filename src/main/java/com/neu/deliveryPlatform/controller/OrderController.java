package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.annotion.IdAccessLimit;
import com.neu.deliveryPlatform.dto.cmd.AddOrderCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateOrderCmd;
import com.neu.deliveryPlatform.service.OrderService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author asiawu
 * @Date 2023-04-05 15:12
 * @Description:
 */

@Api(tags = "订单")
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @ApiOperation(value = "新增订单", notes = "根据传入的订单信息新增订单")
    @PostMapping("/add")
    public Response addOrder(@Valid @RequestBody AddOrderCmd addOrderCmd) {
        return orderService.addOrder(addOrderCmd);
    }

    @ApiOperation(value = "更改订单状态",
            notes = "传入目标状态targetStatus，当targetStatus为2时，需额外传入riderId;" +
                    "当targetStatus为3时，需额外传入订单号后四位，其他情况只需要传订单id")
    @PostMapping("/updateStatus")
    @IdAccessLimit(seconds = 60, maxCount = 10)
    public Response updateOrder(@RequestBody UpdateOrderCmd updateOrderCmd) {
        return orderService.updateOrder(updateOrderCmd);
    }

    @ApiOperation(value = "根据id获取订单", notes = "根据传入的订单id查询订单信息")
    @GetMapping("/getById")
    public Response getOrderById(@RequestParam("id") Long id) {
        return orderService.getOrderById(id);
    }

    @ApiOperation(value = "查询所有订单", notes = "根据当前页数和每页数据条数查询所有，查询所有请传入-1、-1")
    @GetMapping("/getAll")
    public Response getAll(@RequestParam int currentPage, @RequestParam int pageSize) {
        return orderService.getAllOrders(currentPage, pageSize);
    }

    @ApiOperation(value = "根据订单状态查询订单", notes = "1-商家已接单；2-骑手已接单；3-订单完成；4-订单取消")
    @GetMapping("/getByStatus")
    public Response getByStatus(@RequestParam int currentPage, @RequestParam int pageSize, @RequestParam int status) {
        return orderService.getByStatus(currentPage, pageSize, status);
    }

    @ApiOperation(value = "根据骑手Id查询未送达订单", notes = "传入骑手Id，按时间降序返回未送达订单")
    @GetMapping("/getUndoneOrders")
    public Response getUndoneOrders(@RequestParam Long riderId) {
        return orderService.getUndoneOrders(riderId);
    }

    @ApiOperation(value = "根据骑手Id查询已送达订单", notes = "传入骑手Id，按时间降序返回已送达订单")
    @GetMapping("/getDoneOrders")
    public Response getDoneOrders(@RequestParam int currentPage, @RequestParam int pageSize, @RequestParam Long riderId) {
        return orderService.getDoneOrders(currentPage, pageSize, riderId);
    }
}
