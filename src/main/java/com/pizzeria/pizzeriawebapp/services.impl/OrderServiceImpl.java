package com.pizzeria.pizzeriawebapp.services.impl;

import com.pizzeria.pizzeriawebapp.dtos.OrderDTO;
import com.pizzeria.pizzeriawebapp.enums.OrderStatusEnum;
import com.pizzeria.pizzeriawebapp.exceptions.InvalidOrderException;
import com.pizzeria.pizzeriawebapp.exceptions.OrderNotFoundException;
import com.pizzeria.pizzeriawebapp.exceptions.PizzaNotFoundException;
import com.pizzeria.pizzeriawebapp.exceptions.UnauthorizedException;
import com.pizzeria.pizzeriawebapp.models.*;
import com.pizzeria.pizzeriawebapp.payload.request.CreateOrderRequest;
import com.pizzeria.pizzeriawebapp.repositories.*;
import com.pizzeria.pizzeriawebapp.services.OrderService;
import com.pizzeria.pizzeriawebapp.utils.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        User currentLoggedUser = getCurrentLoggedUser();

        Cart cart = currentLoggedUser.getCart();
        List<CartItem> existingCartItems = new ArrayList<>(cart.getCartItems());

        if (existingCartItems.isEmpty()) {
            throw new InvalidOrderException("Cannot create order with an empty cart.");
        }

        Order order = new Order();
        order.setUser(currentLoggedUser);
        order.setDeliveryAddress(createOrderRequest.getDeliveryAddress());
        order.setCustomerTelephone(createOrderRequest.getCustomerTelephone());
        order.setStatus(OrderStatusEnum.PENDING);
        order.setOrderDate(new Date());

        double totalPrice = calculateTotalPrice(existingCartItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        List<CartItem> newCartItems = new ArrayList<>();
        for (CartItem cartItem : existingCartItems) {
            CartItem newCartItem = new CartItem();
            newCartItem.setProductId(cartItem.getProductId());
            newCartItem.setQuantity(cartItem.getQuantity());
            newCartItem.setCreationDate(new Date());
            newCartItem.setCart(cart);
            newCartItem.setOrder(savedOrder);
            newCartItems.add(newCartItem);
        }

        cartItemRepository.deleteAll(existingCartItems);

        cart.setCartItems(newCartItems);
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    private User getCurrentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public List<Order> getUserOrders() {
        User currentlyLoggedUser = getCurrentLoggedUser();

        return orderRepository.findByUser(currentlyLoggedUser);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return OrderMapper.convertToOrderDTO(order, getCurrentLoggedUser());
    }

    @Override
    public List<Order> getAllUserOrders() {
        User currentlyLoggedUser = getCurrentLoggedUser();

        Set<Role> roles = currentlyLoggedUser.getRoles();

        boolean isEmployee = roles.stream()
                .anyMatch(auth -> auth.getName().name().equals("ROLE_EMPLOYEE"));

        if (!isEmployee) {
            throw new UnauthorizedException("Access denied. User is not an employee.");
        }

        return orderRepository.findAll();
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;

        for(CartItem cartItem : cartItems) {
           Pizza pizza = pizzaRepository.findById(cartItem.getProductId())
                   .orElseThrow(() -> new PizzaNotFoundException("Pizza not found"));

           double totalItemPrice = pizza.getPrice() * cartItem.getQuantity();
           totalPrice += totalItemPrice;
        }

        return totalPrice;
    }
}
