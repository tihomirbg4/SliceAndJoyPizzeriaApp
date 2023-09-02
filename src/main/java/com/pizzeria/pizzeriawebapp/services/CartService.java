package com.pizzeria.pizzeriawebapp.services;

import com.pizzeria.pizzeriawebapp.dtos.CartItemDTO;
import com.pizzeria.pizzeriawebapp.dtos.PizzaDTO;
import com.pizzeria.pizzeriawebapp.models.Cart;
import com.pizzeria.pizzeriawebapp.models.CartItem;
import com.pizzeria.pizzeriawebapp.models.User;
import com.pizzeria.pizzeriawebapp.payload.request.CartItemUpdateRequest;

import java.util.List;
import java.util.Set;

public interface CartService {
    void addToCart(CartItem cartItem, User user);

    void removeItem(Long cartItemId);

    void updateCartItem(Long cartItemId, CartItemUpdateRequest cartItem);

    List<PizzaDTO> findAllByProductId();

   List<CartItemDTO> findItemsInCart();

   Long getTotalProductQuantitiesInCart();

}
