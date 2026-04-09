package com.neu.deliveryPlatform.properties;

/**
 * @Author asiawu
 * @Date 2023-05-16 8:48
 * @Description:
 */
public enum OrderStatus {
    /**
     * 商家接单前
     */
    BEFORE_SHOPKEEPER_RECEIVE(0),
    /**
     * 商家接单后（目前默认）
     */
    AFTER_SHOPKEEPER_RECEIVE(1),
    /**
     * 骑手接单后
     */
    AFTER_RIDER_RECEIVE(2),
    /**
     * 订单完成
     */
    ORDER_FINISH(3),
    /**
     * 订单取消
     */
    ORDER_CANCEL(4),
    /**
     * 未知状态
     */
    UNKNOWN_STATUS(-1);

    OrderStatus(int status) {
        this.status = status;
    }

    private final int status;

    public int getStatus() {
        return status;
    }

    public static OrderStatus getEnum(int value) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.getStatus() == value) {
                return orderStatus;
            }
        }
        return UNKNOWN_STATUS;
    }
}
