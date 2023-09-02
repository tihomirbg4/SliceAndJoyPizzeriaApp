package com.pizzeria.pizzeriawebapp.utils;

import com.pizzeria.pizzeriawebapp.dtos.CartItemDTO;
import com.pizzeria.pizzeriawebapp.models.CartItem;
import com.pizzeria.pizzeriawebapp.models.Pizza;

public class CartItemMapper {

    public CartItemMapper() {
    }

    public static CartItemDTO cartItemToDTO(CartItem cartItem, Pizza pizza) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(pizza.getId());
        cartItemDTO.setName(pizza.getName());
        cartItemDTO.setPrice(pizza.getPrice());
        cartItemDTO.setImageUrl(pizza.getImageUrl());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setProductId(pizza.getId());
        cartItemDTO.setCartItemId(cartItem.getId());
        cartItemDTO.setCreationDate(cartItem.getCreationDate());

        return cartItemDTO;
    }
}
