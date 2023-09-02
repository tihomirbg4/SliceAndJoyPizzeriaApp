package com.pizzeria.pizzeriawebapp.services;

import com.pizzeria.pizzeriawebapp.enums.OrderStatusEnum;

public interface EmployeeService {
    void updateOrderStatus(Long orderId, OrderStatusEnum newStatus);
    void changePizzaAvailability(Long pizzaId);
}
