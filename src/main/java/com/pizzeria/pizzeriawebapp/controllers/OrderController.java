package com.pizzeria.pizzeriawebapp.controllers;

import com.pizzeria.pizzeriawebapp.dtos.OrderDTO;
import com.pizzeria.pizzeriawebapp.models.CartItem;
import com.pizzeria.pizzeriawebapp.models.Order;
import com.pizzeria.pizzeriawebapp.payload.request.CreateOrderRequest;
import com.pizzeria.pizzeriawebapp.services.OrderService;
import com.pizzeria.pizzeriawebapp.utils.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pizzas")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize(" hasRole('USER')")
    @PostMapping("/checkout/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        orderService.createOrder(createOrderRequest);
        return ResponseEntity.ok("Order successfully placed");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/checkout/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        List<Order> userOrders = orderService.getUserOrders();
        List<OrderDTO> userOrdersDTOS = new ArrayList<>();

        for(Order order : userOrders) {
            OrderDTO orderDTO = OrderMapper.convertToOrderDTO(order, order.getUser());

            userOrdersDTOS.add(orderDTO);
        }
        return ResponseEntity.ok(userOrdersDTOS);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/checkout/orders/all")
    public ResponseEntity<List<OrderDTO>> getAllUserOrders() {
        List<Order> allUserOrders = orderService.getAllUserOrders();
        List<OrderDTO> allUserOrdersDTO = new ArrayList<>();

        for(Order order : allUserOrders) {
            OrderDTO orderDTO = OrderMapper.convertToOrderDTO(order, order.getUser());

            allUserOrdersDTO.add(orderDTO);
        }

        return ResponseEntity.ok(allUserOrdersDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }
}
