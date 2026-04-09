package com.neu.deliveryPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.dto.cmd.AddOrderCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateOrderCmd;
import com.neu.deliveryPlatform.dto.dto.QueryOrderDto;
import com.neu.deliveryPlatform.entity.*;
import com.neu.deliveryPlatform.filter.JwtAuthenticationTokenFilter;
import com.neu.deliveryPlatform.mapper.*;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.properties.KeyProperties;
import com.neu.deliveryPlatform.properties.OrderStatus;
import com.neu.deliveryPlatform.repository.PlaceRepository;
import com.neu.deliveryPlatform.service.OrderService;
import com.neu.deliveryPlatform.strategy.OrderAsynchronousProcessingFactory;
import com.neu.deliveryPlatform.util.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Asia
 * @description 针对表【order(订单表)】的数据库操作Service实现
 * @createDate 2023-04-05 17:25:22
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${order.upperLimit}")
    private Integer upperLimit;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderShopkeeperMapper orderShopkeeperMapper;

    @Autowired
    OrderCommodityMapper orderCommodityMapper;

    @Autowired
    ShopkeeperMapper shopkeeperMapper;

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    OrderAsynchronousProcessingFactory orderAsynchronousProcessingFactory;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response addOrder(AddOrderCmd addOrderCmd) {
        Order order = new Order();
        BeanUtils.copyProperties(addOrderCmd, order);
        order.setStatus(OrderStatus.AFTER_SHOPKEEPER_RECEIVE.getStatus());
        order.setRiderRemuneration(0.9D);
        order.setCommission(0.1D);
        int cnt1 = orderMapper.insert(order), cnt2 = 0, cnt3 = 0;
        Long orderId = order.getId();
        List<Long> shopkeeperIds = addOrderCmd.getShopkeeperIds();
        List<Long> commodityIds = addOrderCmd.getCommodityIds();
        List<Shopkeeper> shopkeepers = shopkeeperMapper.selectList(null);
        List<Commodity> commodities = commodityMapper.selectList(null);
        //设置商家和商品的销量+1
        for (Long id : shopkeeperIds) {
            for (Shopkeeper shopkeeper : shopkeepers) {
                if (shopkeeper.getId().equals(id)) {
                    shopkeeper.setSales(shopkeeper.getSales() + 1);
                    break;
                }
            }
        }
        for (Long id : commodityIds) {
            for (Commodity commodity : commodities) {
                if (commodity.getId().equals(id)) {
                    commodity.setSales(commodity.getSales() + 1);
                    break;
                }
            }
        }
        //向商品-订单中间表、商家-订单中间表插入数据
        for (Long shopkeeperId : shopkeeperIds) {
            OrderShopkeeper orderShopkeeper = new OrderShopkeeper();
            orderShopkeeper.setOrderId(orderId);
            orderShopkeeper.setShopkeeperId(shopkeeperId);
            cnt2 += orderShopkeeperMapper.insert(orderShopkeeper);
        }
        for (Long commodityId : commodityIds) {
            OrderCommodity orderCommodity = new OrderCommodity();
            orderCommodity.setOrderId(orderId);
            orderCommodity.setCommodityId(commodityId);
            cnt3 += orderCommodityMapper.insert(orderCommodity);
        }
        //redis创建该订单对象
//        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
//        String startingPointLocation = addOrderCmd.getStartingPointAddress();
//        String terminalLocation = addOrderCmd.getTerminalAddress();
//        Location startingPointLocation = placeRepository.findById(order.getStartingPointId()).get().getLocation();
//        Location terminalLocation = placeRepository.findById(order.getTerminalId()).get().getLocation();
//        Map<String, Object> redisOrderInfo = new HashMap<String, Object>(4) {{
//            put(KeyProperties.LOCATION_USER_KEY, startingPointLocation);
//            put(KeyProperties.LOCATION_RIDER_KEY, null);
//            put(KeyProperties.LOCATION_SHOPKEEPER_KEY, terminalLocation);
//            put(KeyProperties.LOCATION_DISTANCE_KEY, 0.0);
//        }};
//        operations.putAll(KeyProperties.LOCATION_ORDER_PREFIX + orderId, redisOrderInfo);

        Map<String, Long> res = new HashMap<String, Long>(1) {{
            put("id", orderId);
        }};
        return cnt1 + cnt2 + cnt3 >= 1 + shopkeeperIds.size() + commodityIds.size() ?
                Response.of(res) : Response.error();
    }

    @Override
    public Response updateOrder(UpdateOrderCmd updateOrderCmd) {
        Integer targetStatus = updateOrderCmd.getTargetStatus();
        Long orderId = updateOrderCmd.getId();
        //利用selectById查询
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BizException(ErrorCode.ORDER_NOT_EXIST);
        }
        if (targetStatus <= order.getStatus()) {
            throw new BizException(ErrorCode.ORDER_STATUS_INVALID);
        }
        OrderStatus targetStatusEnum = OrderStatus.getEnum(targetStatus);
        switch (targetStatusEnum) {
            case AFTER_SHOPKEEPER_RECEIVE: {
                //商家接单，通知骑手（暂时没用）
                break;
            }
            case AFTER_RIDER_RECEIVE: {
                //判断骑手接单数量是否超过上限
                Long riderId = JwtAuthenticationTokenFilter.threadLocal.get();
                String s = stringRedisTemplate.opsForValue().get(KeyProperties.RIDER_STATUS_PREFIX + riderId);
                if (s != null && Integer.parseInt(s) >= upperLimit) {
                    throw new BizException(ErrorCode.RIDER_ORDER_QUANTITY_EXCEED);
                }
                //骑手接单，设置骑手id和开始配送时间
                order.setRiderId(riderId);
                order.setDeliveryTime(new Date());
                //骑手接单量+1
                userMapper.incrRiderTotalOrderQuantity(riderId);
                //骑手接单+1，设为忙碌
                stringRedisTemplate.opsForValue().increment(KeyProperties.RIDER_STATUS_PREFIX + riderId);
                break;
            }
            case ORDER_FINISH: {
                //配送完毕，检验订单号后四位
                Integer orderEndNumber = updateOrderCmd.getOrderEndNumber();
                if (orderEndNumber == null) {
                    throw new BizException(ErrorCode.ID_MISS.getErrCode(), "订单尾号不能为空");
                }
                LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Order::getId, updateOrderCmd.getId())
                        .eq(Order::getEndNumber, orderEndNumber);
                if (!orderMapper.exists(wrapper)) {
                    throw new BizException(ErrorCode.ORDER_END_NUMBER_INVALID);
                }
                //设置结束时间
                order.setEndTime(new Date());

                //异步处理，分配利润+处理接单量+记录明细
                orderAsynchronousProcessingFactory.execute(order);
                break;
            }
            case ORDER_CANCEL: {
                //订单取消
                if (order.getStatus() == OrderStatus.ORDER_FINISH.getStatus()) {
                    throw new BizException(ErrorCode.ORDER_STATUS_HAS_FINISHED);
                }
                //骑手接单量-1
                Long riderId = JwtAuthenticationTokenFilter.threadLocal.get();
                stringRedisTemplate.opsForValue().decrement(KeyProperties.RIDER_STATUS_PREFIX + riderId);
                break;
            }
            default: {
                throw new BizException(ErrorCode.PARAM_ERROR);
            }
        }
        order.setStatus(targetStatus);
        return orderMapper.updateById(order) == 1 ? Response.success() : Response.error();
    }

    @Override
    public Response getOrderById(Long id) {
        Order order = orderMapper.selectById(id);
        if (null != order) {
            return Response.of(getQueryOrderDtos(Collections.singletonList(order)).get(0));
        } else {
            return Response.error(ErrorCode.ID_MISS);
        }
    }

    @Override
    public Response getAllOrders(int nowPage, int perPage) {
        Page<Order> page = new Page<>(nowPage, perPage);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Order::getCreateTime);
        orderMapper.selectPage(page, wrapper);
        return Response.of(page);
    }

    @Override
    public Response getByStatus(int currentPage, int pageSize, int status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        OrderStatus statusEnum = OrderStatus.getEnum(status);
        if (statusEnum == OrderStatus.UNKNOWN_STATUS) {
            throw new BizException(ErrorCode.ORDER_STATUS_INVALID);
        }
        wrapper.eq(Order::getStatus, statusEnum.getStatus())
                .orderByDesc(Order::getCreateTime);
        Page<Order> page = new Page<>(currentPage, pageSize);
        orderMapper.selectPage(page, wrapper);
        List<Order> orderList = page.getRecords();
        List<QueryOrderDto> resRecords = getQueryOrderDtos(orderList);
        Page<QueryOrderDto> resPage = new Page<>();
        resPage.setTotal(page.getTotal());
        resPage.setSize(page.getSize());
        resPage.setCurrent(page.getCurrent());
        resPage.setRecords(resRecords);
        if (resRecords.size() > 0) {
            return Response.of(resPage);
        } else {
            return Response.error(ErrorCode.NO_DATA);
        }
    }

    //传入订单列表，返回带商家名和商品名的订单列表
    @NotNull
    private List<QueryOrderDto> getQueryOrderDtos(List<Order> orders) {
        List<QueryOrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            QueryOrderDto queryOrderDto = new QueryOrderDto();
            Long orderId = order.getId();
            BeanUtils.copyProperties(order, queryOrderDto);
            //添加用戶名
            if (order.getUserId() != null) {
                User user = userMapper.selectById(order.getUserId());
                queryOrderDto.setUserName(user.getUsername());
            }
            //添加骑手名
            if (order.getRiderId() != null) {
                User rider = userMapper.selectById(order.getRiderId());
                queryOrderDto.setRiderName(rider.getUsername());
            }
            //添加商品名
            LambdaQueryWrapper<OrderCommodity> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(OrderCommodity::getOrderId, orderId);
            List<OrderCommodity> orderCommodities = orderCommodityMapper.selectList(wrapper1);
            List<Long> commodityIds = new ArrayList<>();
            List<String> commodityNames = new ArrayList<>();
            orderCommodities.forEach(orderCommodity -> commodityIds.add(orderCommodity.getCommodityId()));
            for (Long commodityId : commodityIds) {
                Commodity commodity = commodityMapper.selectById(commodityId);
                commodityNames.add(commodity.getName());
            }
            //添加商家名
            LambdaQueryWrapper<OrderShopkeeper> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(OrderShopkeeper::getOrderId, orderId);
            List<OrderShopkeeper> orderShopkeepers = orderShopkeeperMapper.selectList(wrapper2);
            List<Long> shopkeeperIds = new ArrayList<>();
            List<String> shopkeeperNames = new ArrayList<>();
            orderShopkeepers.forEach(orderShopkeeper -> shopkeeperIds.add(orderShopkeeper.getShopkeeperId()));
            for (Long shopkeeperId : shopkeeperIds) {
                Shopkeeper shopkeeper = shopkeeperMapper.selectById(shopkeeperId);
                shopkeeperNames.add(shopkeeper.getName());
            }

            queryOrderDto.setCommodityNames(commodityNames);
            queryOrderDto.setShopkeeperNames(shopkeeperNames);
            orderDtos.add(queryOrderDto);
        });
        return orderDtos;
    }

    @Override
    public Response getUndoneOrders(Long riderId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getRiderId,riderId).eq(Order::getStatus,2).orderByDesc(Order::getCreateTime);
        List<Order> orders = orderMapper.selectList(wrapper);
        if (orders.size() > 0) {
            return Response.of(orders);
        } else {
            return Response.error(ErrorCode.NO_DATA);
        }
    }

    @Override
    public Response getDoneOrders(int currentPage, int pageSize, Long riderId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getRiderId, riderId)
                .between(Order::getStatus, OrderStatus.ORDER_FINISH.getStatus(), OrderStatus.ORDER_CANCEL.getStatus())
                .orderByDesc(Order::getCreateTime);
        Page<Order> page = new Page<>(currentPage, pageSize);
        orderMapper.selectPage(page, wrapper);
        return Response.of(page);
    }
}




