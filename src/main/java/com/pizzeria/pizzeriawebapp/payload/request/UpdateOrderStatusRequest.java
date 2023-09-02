package com.pizzeria.pizzeriawebapp.payload.request;

import com.pizzeria.pizzeriawebapp.enums.OrderStatusEnum;

public class UpdateOrderStatusRequest {
    private Long orderId;
    private OrderStatusEnum newStatus;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatusEnum getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatusEnum newStatus) {
        this.newStatus = newStatus;
    }
}
