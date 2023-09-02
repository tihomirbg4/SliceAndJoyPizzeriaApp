package com.pizzeria.pizzeriawebapp.services.impl;

import com.pizzeria.pizzeriawebapp.exceptions.OrderNotFoundException;
import com.pizzeria.pizzeriawebapp.exceptions.PizzaNotFoundException;
import com.pizzeria.pizzeriawebapp.models.Order;
import com.pizzeria.pizzeriawebapp.enums.OrderStatusEnum;
import com.pizzeria.pizzeriawebapp.models.Pizza;
import com.pizzeria.pizzeriawebapp.repositories.OrderRepository;
import com.pizzeria.pizzeriawebapp.repositories.PizzaRepository;
import com.pizzeria.pizzeriawebapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void updateOrderStatus(Long orderId, OrderStatusEnum newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(newStatus);

        orderRepository.save(order);
    }

    @Override
    public void changePizzaAvailability(Long pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new PizzaNotFoundException("Pizza not found"));

        pizza.setIsAvailable(!pizza.getIsAvailable());

        pizzaRepository.save(pizza);
    }
}
