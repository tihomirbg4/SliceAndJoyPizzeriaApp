package com.pizzeria.pizzeriawebapp.services;

import com.pizzeria.pizzeriawebapp.dtos.OrderDTO;
import com.pizzeria.pizzeriawebapp.models.CartItem;
import com.pizzeria.pizzeriawebapp.models.Order;
import com.pizzeria.pizzeriawebapp.payload.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {
    Order createOrder(CreateOrderRequest createOrderRequest);
    List<Order> getUserOrders();
    OrderDTO getOrderById(Long orderId);
    List<Order> getAllUserOrders();
}
